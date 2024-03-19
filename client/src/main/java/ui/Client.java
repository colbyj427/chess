package ui;

import java.util.Arrays;
import java.util.Scanner;
import ui.EscapeSequences;

import static ui.EscapeSequences.*;

public class Client {

  private State state = State.SIGNEDOUT;

  public static void main(String[] args) {
    //var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
    //System.out.println("♕ 240 Chess Client: " + piece);
    new Client().run();
  }

//  String serverUrl;

//  public Repl(String serverUrl) {
//    String serverUrl = this.serverUrl;
//  }
  public void run() {
    System.out.println("♕ Welcome to 240 chess. Type Help to get started.");
    System.out.print(help() + "\n");

    Scanner scanner = new Scanner(System.in);
    var result = "";
    while (!result.equals("quit")) {
      String line = scanner.nextLine();

      try {
        result = eval(line);
        System.out.print(SET_TEXT_COLOR_BLUE + result + "\n");
      } catch (Throwable e) {
        var msg = e.toString();
        System.out.print(msg);
      }
    }
    System.out.println();
  }

  public String signIn(String... params) throws Exception {
    state = state.SIGNEDIN;
    return "You signed in";
    //throw new Exception("Expected: <yourname>");
  }
  public String register(String... params) throws Exception {
    state = state.SIGNEDIN;
    return "You registered";
    //throw new Exception("Expected: <yourname>");
  }
  public String signOut(String... params) throws Exception {
    assertSignedIn();
    state = state.SIGNEDOUT;
    return "You signed out";
    //throw new Exception("Expected: <yourname>");
  }
  public String createGame(String... params) throws Exception {
    assertSignedIn();
    DrawBoard.main();
    return "you created a game";
    //throw new Exception("Expected: <yourname>");
  }
  public String joinGame(String... params) throws Exception {
    assertSignedIn();
    DrawBoard.main();
    return "you joined a game";

    //throw new Exception("Expected: <yourname>");
  }
  public String joinObserver(String... params) throws Exception {
    assertSignedIn();
    DrawBoard.main();
    return "you joined a game as an observer";
    //throw new Exception("Expected: <yourname>");
  }
  public String listGames(String... params) throws Exception {
    assertSignedIn();
    return "list of games";
    //throw new Exception("Expected: <yourname>");
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
      outputString+="JoinGame <game name> <team>\n";
      outputString+="ListGames\n";
      outputString+="JoinObserver <game name>";
      return outputString;
    }
  }
  private String eval(String input) {
    try {
      var tokens = input.toLowerCase().split(" ");
      var cmd = (tokens.length > 0) ? tokens[0] : "help";
      var params = Arrays.copyOfRange(tokens, 1, tokens.length);
      return switch (cmd) {
        case "login" -> signIn(params);
        case "register" -> register(params);
        case "logout" -> signOut();
        case "creategame" -> createGame(params);
        case "joingame" -> joinGame(params);
        case "listgames" -> listGames();
        case "joinobserver" -> joinObserver();
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
  public enum State {
    SIGNEDOUT,
    SIGNEDIN
  }
}
