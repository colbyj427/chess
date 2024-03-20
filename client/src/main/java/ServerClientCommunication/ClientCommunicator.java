package ServerClientCommunication;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;


public class ClientCommunicator {
  static String serverURL = "http://localhost:";
  static public int port;
  public static <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws Exception {
    try {
      makeURL(port);
      URL url = (new URI(serverURL + path)).toURL();
      HttpURLConnection http = (HttpURLConnection) url.openConnection();
      http.setRequestMethod(method);
      http.setDoOutput(true);
      if (authToken != null) {
        http.addRequestProperty("Authorization", authToken);
      }
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
      throw new Exception("failure: " + status);
    }
  }
  private static boolean isSuccessful(int status) {
    return status / 100 == 2;
  }
  private static void makeURL(int port) {
    serverURL = "http://localhost:";
    serverURL += Integer.valueOf(port);
    serverURL += "/";
  }
}
