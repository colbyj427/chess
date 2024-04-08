package ui;

import ServerClientCommunication.ServerFacade;
import ServerClientCommunication.ServerMessageObserver;
import ServerClientCommunication.WebSocketFacade;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.AuthRecord;
import model.GameRecord;
import model.JoinGameRecord;
import model.UserRecord;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;

import java.lang.reflect.Type;
import java.text.CollationElementIterator;
import java.util.*;

import static ui.EscapeSequences.*;

public class Client implements ServerMessageObserver {
  private State state = State.SIGNEDOUT;
  private String authToken = "";
  private String username = "";
  private Map<Integer, Integer> gameDict = new HashMap<>();
  private int port = 8080;
  private String serverURL;
  //private ServerMessageObserver notificationHandler;
  private WebSocketFacade ws;
  private GameRecord currentGame;
  private String playerColor = null;


  public static void main(String[] args) {
    new Client().run();
  }

  public void run() {
    this.serverURL = "http://localhost:" + Integer.valueOf(port);
    ServerFacade facade = new ServerFacade(8080);
    System.out.println("♕ Welcome to 240 chess. Type Help to get started.");
    System.out.print(help() + "\n");


    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      if (state == state.SIGNEDOUT) {
        System.out.print(">>> ");
      }
      else {
        System.out.print("Chess240 >>> ");
      }
      String line = scanner.nextLine();
      try {
        result = eval(line);
        System.out.print(SET_TEXT_COLOR_BLUE + result + "\n");
      } catch (Throwable e) {
        var msg = e.toString();
        System.out.print(msg);
      }
      finally {
        if (result == "quit") {
          result = "quit";
        }
        else {
          result="";
        }
      }
    }
    System.out.println();
  }

  public String logIn(String... params) throws Exception {
    assertSignedOut();
    try {
      if (params.length != 2) {
        throw new Exception("Expected: <username> <password>");
      }
        UserRecord registerRequest=new UserRecord(params[0], params[1], null);
        AuthRecord response=ServerFacade.login(registerRequest);
        authToken = response.authToken();
        username = response.username();
        state=state.SIGNEDIN;
        return "Welcome to chess 240";
      }
    catch (Exception exception) {
      throw new Exception("Incorrect login info");
    }
  }
  public String register(String... params) throws Exception {
    assertSignedOut();
    try {
      if (params.length != 3) {
        throw new Exception("Expected <username> <password> <email>");
      }
      UserRecord registerRequest=new UserRecord(params[0], params[1], params[2]);
      AuthRecord response=ServerFacade.register(registerRequest);
      authToken = response.authToken();
      username = response.username();
      state=state.SIGNEDIN;
      return "You have been registered\nWelcome to chess240";
    }
    catch (Exception exception){
      if (exception.getMessage()  == null) {
        throw new Exception("User already taken");
      }
      throw new Exception(exception.getMessage());
    }
  }
  public String logOut(String... params) throws Exception {
    assertSignedIn();
    try {
      ServerFacade.logout(null, authToken);
      this.authToken = "";
      this.username = "";
      state = state.SIGNEDOUT;
      return "logged out";
    }
    catch (Exception exception) {
      throw new Exception(exception.getMessage());
    }
  }
  public String createGame(String... params) throws Exception {
    assertSignedIn();
    try {
      if (params.length != 1) {
        throw new Exception("Expected <game name>");
      }
      String gameName=params[0];
      GameRecord gameRecord=new GameRecord(0, null, null, gameName, null, null);
      GameRecord newGameRecord=ServerFacade.createGame(gameRecord, authToken);
      String newGameName = String.valueOf(newGameRecord.gameName());
      ChessGame newGame = newGameRecord.game();
      DrawBoard.main(newGame.getBoard().getBoardLayout(), "WHITE", null);
      return "Game created with name: " + newGameName;
    } catch (Exception exception) {
      if (exception.getMessage() == null) {
        throw new Exception("Game name already taken");
      }
      throw new Exception(exception.getMessage());
    }
  }
  public String joinGame(String... params) throws Exception {
    assertSignedIn();
    try {
      if (params.length != 2) {
        throw new Exception("Expected <game ID> <team>");
      }
      int gameNum=Integer.valueOf(params[0]);
      String team = params[1].toUpperCase();
      JoinGameRecord joinGameRecord=new JoinGameRecord(team, getGameID(gameNum));
      GameRecord newGameRecord=ServerFacade.joinGame(joinGameRecord, authToken);
      currentGame = newGameRecord;
      playerColor = team;
      ChessGame newGame = newGameRecord.game();
      DrawBoard.main(newGame.getBoard().getBoardLayout(), team, null);
      //****
      //create a websocket facade and pass the client into it
      ws = new WebSocketFacade(serverURL, this);
      ws.joinPlayer(authToken, username, team, newGameRecord.gameID());
      state = State.INGAME;
      //****
      return "You have joined the game";
    } catch (Exception exception) {
      if (exception.getMessage() == null) {
        throw new Exception("Invalid gameID");
      }
      throw new Exception(exception.getMessage());
    }
  }

  private int getGameID(int gameNum) {
    return gameDict.get(gameNum);
  }

  public String joinObserver(String... params) throws Exception {
    assertSignedIn();
    try {
      if (params.length != 1) {
        throw new Exception("Expected <game id>");
      }

      int gameNum=Integer.valueOf(params[0]);
      JoinGameRecord joinGameRecord=new JoinGameRecord(null, getGameID(gameNum));
      GameRecord newGameRecord=ServerFacade.joinGame(joinGameRecord, authToken);
      ChessGame newGame = newGameRecord.game();
      DrawBoard.main(newGame.getBoard().getBoardLayout(), "WHITE", null);
      //****
      ws = new WebSocketFacade(serverURL, this);
      ws.joinObserver(authToken, username, newGameRecord.gameID());
      //****
      state = State.INGAME;
      return "You are observing the game";
    } catch (Exception exception) {
      if (exception.getMessage() == null) {
        throw new Exception("Invalid gameID");
      }
      throw new Exception(exception.getMessage());
    }
  }
  public String listGames(String... params) throws Exception {
    assertSignedIn();
    try {
      JsonObject games=ServerFacade.listGames(null, authToken);
      Type collectionType = new TypeToken<Collection<GameRecord>>(){}.getType();
      Collection<GameRecord> listOfGames = new Gson().fromJson(games.get("games"), collectionType);
      String listOfGamesString = "";
      int counter = 0;
      for (GameRecord game: listOfGames) {
        counter += 1;
        listOfGamesString+=prettyGameString(game, counter);
        gameDict.put(counter, game.gameID());
      }
      return listOfGamesString;
    } catch (Exception exception) {
      throw new Exception(exception.getMessage());
    }
  }

  private static String prettyGameString(GameRecord game, int numGame) {
    String gameString = "";
    gameString += numGame;
    gameString += ". ";
    gameString+= "Game: ";
    gameString+= game.gameName();
    gameString+= "; White Player: ";
    gameString+= game.whiteUsername();
    gameString+= "; Black Player: ";
    gameString+= game.blackUsername();
    gameString+= ";\n";
    return gameString;
  }
  public String leave(String... params) throws Exception {
    assertInGame();
    try {
      ws.leave(authToken, username, currentGame.gameID());
      currentGame = null;
      playerColor = null;
      state = State.SIGNEDIN;
      return "You left the game.";
    } catch (Exception exception) {
      throw new Exception(exception.getMessage());
    }
  }
  public String resign(String... params) throws Exception {
    assertInGame();
    try {
      if (currentGame == null) {
        return "You are not in a valid game.";
      }
      ws.resign(authToken, username, currentGame.gameID());
      currentGame = null;
      playerColor = null;
      state = State.SIGNEDIN;
      return "You forfeited the match.";
    } catch (Exception exception) {
      throw new Exception(exception.getMessage());
    }
  }
  public String makeMove(String... params) throws Exception {
    assertInGame();
    if (!playerColor.equals((currentGame.game().getTeamTurn().toString()))) {
      return "Not your turn.";
    }
    if (currentGame.game().isInCheckmate(ChessGame.TeamColor.WHITE) ||
            currentGame.game().isInCheckmate(ChessGame.TeamColor.BLACK) ||
            currentGame.game().isInStalemate(ChessGame.TeamColor.WHITE) ||
            currentGame.game().isInStalemate(ChessGame.TeamColor.BLACK)) {
      return "Game is over.";
    }
    try {
      if (currentGame == null) {
        return "You are not in a valid game.";
      }
      ChessPosition from = parseMove(params[0]);
      ChessPosition to = parseMove(params[1]);
      ChessMove move = new ChessMove(from, to, null);
      String moveString = from + " " + to;
      ws.makeMove(authToken, username, currentGame.gameID(), playerColor, move, moveString);
      return "";
    } catch (Exception exception) {
      throw new Exception(exception.getMessage());
    }
  }
  public String redraw(String... params) throws Exception{
    assertInGame();
    DrawBoard.main(currentGame.game().getBoard().getBoardLayout(), playerColor, null);
    return "";
  }
  public String highlightMoves(String... params) throws Exception {
    assertInGame();
    try {
      if (params.length != 1) {
        throw new Exception("Expected: <position>");
      }
    } catch (Exception e) {
      return "Expected: <position>";
    }
    ChessPosition position = parseMove(params[0]);
    Collection<ChessMove> validMoves = currentGame.game().validMoves(position);
    Collection<ChessPosition> endPositions = new ArrayList<>();
    for (ChessMove move: validMoves) {
      endPositions.add(move.getEndPosition());
    }
    DrawBoard.main(currentGame.game().getBoard().getBoardLayout(), playerColor, endPositions);
    return "Valid moves highlighted.";
  }
  public String clear(String... params) throws Exception {
    assertSignedOut();
    try {
      ServerFacade.clear(null);
      return "";
    } catch (Exception exception) {
      throw new Exception(exception.getMessage());
    }
  }

  private String help() {
    if (state == state.SIGNEDOUT) {
      String outputString="";
      outputString+="Help: display commands and what they do.\n";
      outputString+="Quit: end the program.\n";
      outputString+="Login <username> <password> : login to chess240 with a username and password.\n";
      outputString+="Register <username> <password> <email> : register as a user to chess 240.";
      return outputString;
    }
    else if (state == state.SIGNEDIN) {
      String outputString="";
      outputString+="Help: display commands and what they do.\n";
      outputString+="Logout: logout of chess and return to main menu.\n";
      outputString+="CreateGame <game name> : create a new chess game with the given name.\n";
      outputString+="JoinGame <game id> <team> : join a game at the number listed from listgames as the specified team.\n";
      outputString+="ListGames: show a list of all created games.\n";
      outputString+="JoinObserver <game id> : Join the specified game from the listed games as an observer.";
      return outputString;
    }
    else {
      String outputString="";
      outputString+="Help: display commands and what they do.\n";
      outputString+="Redraw: Redraw the chess board.\n";
      outputString+="Leave: Leave the game, your color can be taken by someone else.\n";
      outputString+="MakeMove: Input the move you would like to make, if valid it will be executed.\n";
      outputString+="Resign: You will forfeit the game.\n";
      outputString+="HighlightMoves: All legal moves will be highlighted for your color.";
      return outputString;
    }
  }
  private String eval(String input) {
    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "login" -> logIn(params);
        case "register" -> register(params);
        case "logout" -> logOut();
        case "creategame" -> createGame(params);
        case "joingame" -> joinGame(params);
        case "listgames" -> listGames();
        case "joinobserver" -> joinObserver(params);
        case "redraw", "r" -> redraw(params);
        case "leave" -> leave(params);
        case "makemove" -> makeMove(params);
        case "resign" -> resign(params);
        case "highlightmoves" -> highlightMoves(params);
        case "clear" -> clear();
        case "quit" -> "quit";
        default -> help();
      };
    } catch (Exception ex) {
      return ex.getMessage();
    }
  }
  private void assertSignedIn() throws Exception {
    if (state == State.SIGNEDOUT) {
      throw new Exception("You must log in");
    }
  }
  private void assertSignedOut() throws Exception {
    if (state == State.SIGNEDIN) {
      throw new Exception("You must log out");
    }
  }
  private void assertInGame() throws Exception {
    if (state != State.INGAME) {
      throw new Exception("You must be in a game");
    }
  }
  @Override
  public void notify(String notification) {
    ServerMessage message = new Gson().fromJson(notification, ServerMessage.class);
    System.out.println(message.getServerMessageType());
    switch (message.getServerMessageType()) {
      case NOTIFICATION -> handleNotification(notification);
      case LOAD_GAME -> handleLoadGame(notification);
      case ERROR -> handleError(notification);
    }
  }
  public void handleNotification(String notification) {
    NotificationMessage message = new Gson().fromJson(notification, NotificationMessage.class);
    System.out.print(message.getMessage());
  }
  public void handleLoadGame(String notification) {
    LoadGameMessage message = new Gson().fromJson(notification, LoadGameMessage.class);
    ChessGame game = message.getGame();
    DrawBoard.main(game.getBoard().getBoardLayout(), playerColor, null);
    currentGame = new GameRecord(currentGame.gameID(), currentGame.whiteUsername(), currentGame.blackUsername(), currentGame.gameName(), game, currentGame.spectators());
  }
  public void handleError(String notification) {
    ErrorMessage message = new Gson().fromJson(notification, ErrorMessage.class);
    System.out.print(message.getErrorMessage());
  }

  public ChessPosition parseMove(String... params) {
    char firstChar = params[0].charAt(0);
    int y = firstChar - 'a' + 1;
    int x = Integer.parseInt(params[0].substring(1));
    return new ChessPosition(x,y);
  }

  public enum State {
    SIGNEDOUT,
    SIGNEDIN,
    INGAME
  }
}
