package server.WebSocket;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.Server;
import service.GameService;
import webSocketMessages.serverMessages.ErrorMessage;
import webSocketMessages.serverMessages.LoadGameMessage;
import webSocketMessages.serverMessages.NotificationMessage;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;


import java.io.IOException;

@WebSocket
public class WebSocketHandler {

  private final ConnectionManager connections = new ConnectionManager();

  @OnWebSocketMessage
  public void onMessage(Session session, String message) throws Exception {
    UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
    switch (command.getCommandType()) {
      case JOIN_PLAYER -> joinPlayer(message, session);
      case JOIN_OBSERVER -> joinObserver(message, session);
      case MAKE_MOVE -> makeMove(message, session);
      case LEAVE -> leave(message, session);
      case RESIGN -> resign(message, session);
      case null -> joinPlayer(message, session);
    }
  }
  private void joinPlayer(String jsonString, Session session) throws IOException {
    JoinPlayerCommand command = new Gson().fromJson(jsonString, JoinPlayerCommand.class);
    connections.add(command.getAuthString(), session);
    var message = String.format("%s has joined the game as %s.", command.getUsername(), command.getPlayerColor());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    connections.broadcast(command.getAuthString(), notification);
  }
  private void joinObserver(String jsonString, Session session) throws IOException {
    UserGameCommand command = new Gson().fromJson(jsonString, JoinObserverCommand.class);
    connections.add(command.getAuthString(), session);
    var message = String.format("%s is observing the game.", command.getUsername());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    connections.broadcast(command.getAuthString(), notification);
  }
  private void leave(String jsonString, Session session) throws IOException {
    UserGameCommand command = new Gson().fromJson(jsonString, LeaveCommand.class);
    var message = String.format("%s has left the game.", command.getUsername());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    connections.broadcast(command.getAuthString(), notification);
    connections.remove(command.getAuthString());
  }
  private void resign(String jsonString, Session session) throws IOException {
    UserGameCommand command = new Gson().fromJson(jsonString, ResignCommand.class);
    var message = String.format("%s has forfeited the game.", command.getUsername());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    connections.broadcast(command.getAuthString(), notification);
    connections.remove(command.getAuthString());
  }
  private void makeMove(String jsonString, Session session) throws Exception {
    MakeMoveCommand command=new Gson().fromJson(jsonString, MakeMoveCommand.class);
    ChessGame game = Server.memoryGameDao.getGame(command.getGameId()).game();
    try {
      game.makeMove(command.getMove());
      //checks if either color is in checkmate or stalemate and if so the game needs to end.
      if (game.isInCheckmate(ChessGame.TeamColor.WHITE) || game.isInCheckmate(ChessGame.TeamColor.BLACK)){
        var message=String.format("%s has won the game.", command.getUsername());
        var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast("", notification);
      }
      if (game.isInStalemate(ChessGame.TeamColor.WHITE) || game.isInStalemate(ChessGame.TeamColor.BLACK)) {
        String message = "STALEMATE";
        var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast("", notification);
      }
      Server.memoryGameDao.updateGame(game, command.getGameId()); //update the database after making the move.
      var message=String.format("%s moved: %s.", command.getUsername(), command.getMoveString());
      var notification=new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
      var loadGame=new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game, command.getColor());
      connections.broadcast("", loadGame);
      connections.broadcast(command.getAuthString(), notification);
    } catch (InvalidMoveException e) {
      var message=String.format("Invalid move: %s", e.getMessage());
      var error=new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
      connections.broadcast("", error); //broadcasting to everyone, probably should only go to the player who made the move.
    }
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
