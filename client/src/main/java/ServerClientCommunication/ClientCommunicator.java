package ServerClientCommunication;

import com.google.gson.Gson;
import dataAccess.DataAccessException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ClientCommunicator {
  static String serverURL = "http://localhost:8080/";
  public static <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws Exception {
    try {
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
//  public static String post(String urlString, String header, String body) throws Exception {
//    URL url = new URL(urlString);
//    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//    connection.setReadTimeout(5000);
//    connection.setRequestMethod("POST");
//    connection.setDoOutput(true);
//
//    // Set HTTP request headers, if necessary
//    connection.addRequestProperty("Authorization", "header");
//
//    // Convert the request body to bytes
//    byte[] postData = body.getBytes(StandardCharsets.UTF_8);
//    try(OutputStream wr = connection.getOutputStream();) {
//      wr.write(postData);
//    }
//
//    int responseCode = connection.getResponseCode();
//
//    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//      //only need to do this if i need the headers but at this level i shouldn't need anything out of it.
//      // Get HTTP response headers, if necessary
//      //Map<String, List<String>> headers = connection.getHeaderFields();
//      // OR
//      //connection.getHeaderField("Content-Length");
//
//      // probably don't even need to use this?
//      InputStream responseBody = connection.getInputStream();
//      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//      String inputLine;
//      StringBuffer response = new StringBuffer();
//      while ((inputLine = in.readLine()) != null) {
//        response.append(inputLine);
//      }
//      return response.toString();
//    }
//    else {
//      // SERVER RETURNED AN HTTP ERROR
//      InputStream responseBody = connection.getInputStream();
//      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//      String inputLine;
//      StringBuffer response = new StringBuffer();
//      while ((inputLine = in.readLine()) != null) {
//        response.append(inputLine);
//      }
//      throw new DataAccessException(responseCode, responseBody.toString());
//    }
//  }
//  public static String delete(String request) {
//    return "";
//  }
//  public static String get(String request) {
//    return "";
//  }
//  public static String put(String request) {
//    return "";
//  }
}
