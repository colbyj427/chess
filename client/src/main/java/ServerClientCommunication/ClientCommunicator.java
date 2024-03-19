package ServerClientCommunication;

import dataAccess.DataAccessException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class ClientCommunicator {
  public static String post(String urlString, String header, String body) throws Exception {
    URL url = new URL(urlString);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setReadTimeout(5000);
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);

    // Set HTTP request headers, if necessary
    connection.addRequestProperty("Authorization", "header");

    // Convert the request body to bytes
    byte[] postData = body.getBytes(StandardCharsets.UTF_8);
    try(OutputStream wr = connection.getOutputStream();) {
      wr.write(postData);
    }

    int responseCode = connection.getResponseCode();

    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
      //only need to do this if i need the headers but at this level i shouldn't need anything out of it.
      // Get HTTP response headers, if necessary
      //Map<String, List<String>> headers = connection.getHeaderFields();
      // OR
      //connection.getHeaderField("Content-Length");

      // probably don't even need to use this?
      InputStream responseBody = connection.getInputStream();
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      return response.toString();
    }
    else {
      // SERVER RETURNED AN HTTP ERROR
      InputStream responseBody = connection.getInputStream();
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String inputLine;
      StringBuffer response = new StringBuffer();
      while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
      }
      throw new DataAccessException(responseCode, responseBody.toString());
    }
  }
  public static String delete(String request) {
    return "";
  }
  public static String get(String request) {
    return "";
  }
  public static String put(String request) {
    return "";
  }
}
