package ui;

import ServerClientCommunication.ServerFacade;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import dataAccess.DataAccessException;
import model.AuthRecord;
import model.GameRecord;
import model.JoinGameRecord;
import model.UserRecord;
import org.eclipse.jetty.server.Authentication;

import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.sun.tools.javac.util.StringUtils.toUpperCase;
import static ui.EscapeSequences.*;

public class Client {
  private State state = State.SIGNEDOUT;

  private String authToken = "";
  private Map<Integer, Integer> gameDict = new HashMap<>();


  public static void main(String[] args) {
    new Client().run();
  }

  public void run() {
    System.out.println("♕ Welcome to 240 chess. Type Help to get started.");
    System.out.print(help() + "\n");


    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      if (state == state.SIGNEDIN) {
        System.out.print("Chess240 >>> ");
      }
      else {
        System.out.print(">>> ");
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
        String username = params[0];
        String password = params[1];
        UserRecord registerRequest=new UserRecord(params[0], params[1], null);
        AuthRecord response=ServerFacade.login(registerRequest);
        authToken = response.authToken();
        state=state.SIGNEDIN;
        return "";
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
      state=state.SIGNEDIN;
      return "";
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
      state = state.SIGNEDOUT;
      return "";
    }
    catch (Exception exception) {
      throw new Exception(exception.getMessage());
    }
  }
  public String createGame(String... params) throws Exception { //isnt throwing error when gamename is the same.
    assertSignedIn();
    try {
      if (params.length != 1) {
        throw new Exception("Expected <game name>");
      }
      String gameName=params[0];
      GameRecord gameRecord=new GameRecord(0, null, null, gameName, null, null);
      GameRecord newGameRecord=ServerFacade.createGame(gameRecord, authToken);
      String newGameId = String.valueOf(newGameRecord.gameID());
      DrawBoard.main();
      return "Game created with ID: " + newGameId;
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
      DrawBoard.main();
      return "";
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
      DrawBoard.main();
      return "";
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
        listOfGamesString+=prettyGame(game, counter);
        gameDict.put(counter, game.gameID());
      }
      return listOfGamesString;
    } catch (Exception exception) {
      throw new Exception(exception.getMessage());
    }
  }

  private static String prettyGame(GameRecord game, int numGame) {
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
  public String clear(String... params) throws Exception {
    assertSignedIn();
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
      outputString+="Help\n";
      outputString+="Quit\n";
      outputString+="Login <username> <password>\n";
      outputString+="Register <username> <password> <email>";
      return outputString;
    }
    else {
      String outputString="";
      outputString+="Help\n";
      outputString+="Logout\n";
      outputString+="CreateGame <game name>\n";
      outputString+="JoinGame <game id> <team>\n";
      outputString+="ListGames\n";
      outputString+="JoinObserver <game id>";
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
  public enum State {
    SIGNEDOUT,
    SIGNEDIN
  }
}
