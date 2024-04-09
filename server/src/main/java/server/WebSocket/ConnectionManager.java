package server.WebSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
  public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
  // have a map of authtokens mapped to sessions.
  Map<Integer, List<String>> games = new HashMap<>();
  //have a map with gameid as a key, list of players and observers as values.

  public void addUser(String authToken, Session session, int gameId) {
    var connection = new Connection(authToken, session);
    connections.put(authToken, connection);
    if (games.containsKey(gameId)) {
      games.get(gameId).add(authToken);
    } else {
      var list = new ArrayList<String>();
      list.add(authToken);
      games.put(gameId, list);
    }
  }

  public void remove(String visitorName) {
    connections.remove(visitorName);
  }

  public void broadcast(String excludePlayerName, int gameId, ServerMessage notification) throws IOException {
    var removeList=new ArrayList<Connection>();
    // get all the authtokens associated with gameid
    // add them to a list, and iterate through it.
    // for each authtoken, send the notification unless it equals exclude string.
    List<String> authTokens = games.get(gameId);
    for (String authToken : authTokens) {
      if (!authToken.equals(excludePlayerName)) {
        Connection c = connections.get(authToken);
        if (c.session.isOpen()) {
          c.send(new Gson().toJson(notification));
        } else {
          removeList.add(c);
        }
      }
    }
    // Clean up any connections that were left open.
    for (var c : removeList) {
      connections.remove(c.visitorName);
    }
  }
  public void rootBroadcast(String authToken, ServerMessage notification) throws IOException {
    Connection c = connections.get(authToken);
    if (c.session.isOpen()) {
      c.send(new Gson().toJson(notification));
    }
  }
}