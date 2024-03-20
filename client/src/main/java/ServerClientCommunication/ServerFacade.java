package ServerClientCommunication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dataAccess.DataAccessException;
import model.AuthRecord;
import model.GameRecord;
import model.JoinGameRecord;
import model.UserRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Collection;

import static ServerClientCommunication.ClientCommunicator.*;

public class ServerFacade {
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
  public static GameRecord joinGame(JoinGameRecord registerRequest, String authToken) throws Exception {
    return makeRequest("PUT", "game", registerRequest, null, authToken);
  }
  public static String clear(String registerRequest) throws Exception {
    return makeRequest("DELETE", "db", null, null, null);
  }
}
