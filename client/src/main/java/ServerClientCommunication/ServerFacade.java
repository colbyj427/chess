package ServerClientCommunication;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.AuthRecord;
import model.UserRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
  static String serverURL = "http://localhost:8080/";
  public static AuthRecord register(UserRecord registerRequest) throws Exception {
    return makeRequest("POST", "user", registerRequest, AuthRecord.class);

  }
  public static String login(String url, String header, String body) throws Exception {

    return ClientCommunicator.post(url, header, body);
  }
  private static <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws Exception {
    try {
      URL url = (new URI(serverURL + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);

      writeBody(request, http);
      http.connect();
      throwIfNotSuccessful(http);
      return readBody(http, responseClass);
    } catch (Exception ex) {
      throw new Exception(ex.getMessage());
    }
  }
  private static void writeBody(Object request, HttpURLConnection http) throws IOException {
    if (request != null) {
      http.addRequestProperty("Content-Type", "application/json");
      String reqData = new Gson().toJson(request);
      try (OutputStream reqBody = http.getOutputStream()) {
        reqBody.write(reqData.getBytes());
      }
    }
  }
  private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
    T response = null;
    if (http.getContentLength() < 0) {
      try (InputStream respBody = http.getInputStream()) {
        InputStreamReader reader = new InputStreamReader(respBody);
        if (responseClass != null) {
          response = new Gson().fromJson(reader, responseClass);
        }
      }
    }
    return response;
  }
  private static void throwIfNotSuccessful(HttpURLConnection http) throws IOException, Exception {
    var status = http.getResponseCode();
    if (!isSuccessful(status)) {
      throw new Exception();
    }
  }
  private static boolean isSuccessful(int status) {
    return status / 100 == 2;
  }
  public static String logout(String request) {
    return "";
  }
  public static String listGames(String request) {
    return "";
  }
  public static String createGame(String request) {
    return "";
  }
  public static String joinGame(String request) {
    return "";
  }
  public static String clear(String request) {
    return "";
  }
}
