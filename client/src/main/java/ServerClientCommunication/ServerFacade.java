package ServerClientCommunication;

import com.google.gson.JsonObject;
import model.AuthRecord;
import model.GameRecord;
import model.JoinGameRecord;
import model.UserRecord;



import static ServerClientCommunication.HttpCommunicator.*;

public class ServerFacade {
  final int port;
  public ServerFacade(int port) {
    this.port = port;
    HttpCommunicator.port = port;
  }

  public static AuthRecord register(UserRecord registerRequest) throws Exception {
    return makeRequest("POST", "user", registerRequest, AuthRecord.class, null);
  }
  public static AuthRecord login(UserRecord registerRequest) throws Exception {
    return makeRequest("POST", "session", registerRequest, AuthRecord.class, null);
  }
  public static String logout(AuthRecord registerRequest, String authToken) throws Exception {
    return makeRequest("DELETE", "session", null, null, authToken); //check the return type later.
  }
  public static JsonObject listGames(AuthRecord registerRequest, String authToken) throws Exception {
    return makeRequest("GET", "game", null, JsonObject.class, authToken);
  }
  public static GameRecord createGame(GameRecord registerRequest, String authToken) throws Exception {
    return makeRequest("POST", "game", registerRequest, GameRecord.class, authToken);
  }
  //when user joins or observes a game, websocket connection should be established.
  public static GameRecord joinGame(JoinGameRecord registerRequest, String authToken) throws Exception {
    return makeRequest("PUT", "game", registerRequest, GameRecord.class, authToken);
  }
  public static String clear(String registerRequest) throws Exception {
    return makeRequest("DELETE", "db", null, null, null);
  }
}
