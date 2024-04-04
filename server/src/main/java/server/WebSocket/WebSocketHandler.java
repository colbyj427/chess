package server.WebSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.JoinPlayerCommand;
import webSocketMessages.userCommands.LeaveCommand;
import webSocketMessages.userCommands.UserGameCommand;


import java.io.IOException;

@WebSocket
public class WebSocketHandler {

  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {
    UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
    switch (command.getCommandType()) {
      case JOIN_PLAYER -> joinPlayer(message, session);
      case JOIN_OBSERVER -> {}
      case MAKE_MOVE -> {}
      case LEAVE -> leave(message, session);
      case RESIGN -> {}
      case null -> joinPlayer(message, session);
    }
  }
  private void joinPlayer(String jsonString, Session session) throws IOException {
    UserGameCommand command = new Gson().fromJson(jsonString, JoinPlayerCommand.class);
    connections.add(command.getAuthString(), session);
    var message = String.format("Player %s has joined the game.", command.getUsername());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    connections.broadcast(command.getAuthString(), notification);
  }
  private void leave(String jsonString, Session session) throws IOException {
    UserGameCommand command = new Gson().fromJson(jsonString, JoinPlayerCommand.class);
    //connections.remove(authToken);
    var message = String.format("Player %s has left the game.", command.getUsername());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    connections.broadcast(command.getAuthString(), notification);
  }

//  private void enter(String visitorName, Session session) throws IOException {
//    connections.add(visitorName, session);
//    var message = String.format("%s is in the shop", visitorName);
//    var notification = new Notification(Notification.Type.ARRIVAL, message);
//    connections.broadcast(visitorName, notification);
//  }
//
//  private void exit(String visitorName) throws IOException {
//    connections.remove(visitorName);
//    var message = String.format("%s left the shop", visitorName);
//    var notification = new Notification(Notification.Type.DEPARTURE, message);
//    connections.broadcast(visitorName, notification);
//  }

//  public void makeNoise(String petName, String sound) throws Exception {
//    try {
//      var message = String.format("%s says %s", petName, sound);
//      var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
//      connections.broadcast("", notification);
//    } catch (Exception ex) {
//      throw new Exception(ex.getMessage());
//    }
//  }
}
