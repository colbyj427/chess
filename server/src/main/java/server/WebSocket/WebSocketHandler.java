package server.WebSocket;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.LeaveCommand;
import webSocketMessages.userCommands.UserGameCommand;


import java.io.IOException;

@WebSocket
public class WebSocketHandler {

  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws IOException {
    UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
    //switch (UserGameCommand.CommandType()) {
      //case Join_Player -> enter(action.visitorName(), session);
      //case JoinObserver -> exit(action.visitorName());
   // }
  }

  private void leave(String authToken, Session session) throws IOException{
    connections.remove(authToken);
    var message = String.format("%s has left the game.", authToken);
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    connections.broadcast("", notification);
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
