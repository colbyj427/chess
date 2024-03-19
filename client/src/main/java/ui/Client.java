package ui;

import ServerClientCommunication.ServerFacade;
import dataAccess.DataAccessException;
import model.AuthRecord;
import model.UserRecord;
import org.eclipse.jetty.server.Authentication;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Client {
  private State state = State.SIGNEDOUT;

  //mak an auth token


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
//    try {
//      if (params.length >= 2){
//        String username = params[0];
//        String password = params[1];
//        String body = String.format("\"username\":\"%s\" \"password\":\"%s\"", username, password);
//        ServerFacade.login(serverUrl, "", body);
//        state=state.SIGNEDIN;
//        return "You signed in";
//      }
//      throw new Exception("Expected: <username> <password>");
//    }
//    catch (Exception exception) {
//      //throw new DataAccessException(400, "wrong user information");
//      throw new Exception(exception.getMessage());
//    }
    return "";
  }
  public String register(String... params) throws Exception {
    try {
      if (params.length != 3) {
        throw new Exception("Expected <username> <password> <email>");
      }
      UserRecord registerRequest=new UserRecord(params[0], params[1], params[2]); // check that there are enough paramerters.
      AuthRecord response=ServerFacade.register(registerRequest);
      System.out.println(response.authToken());
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
        case "login" -> logIn(params);
        case "register" -> register(params);
        case "logout" -> logOut();
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
