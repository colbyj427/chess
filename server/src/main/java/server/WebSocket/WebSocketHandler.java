package server.WebSocket;

import chess.ChessGame;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import model.GameRecord;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.Server;
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
  private void joinPlayer(String jsonString, Session session) throws IOException, DataAccessException {
    JoinPlayerCommand command = new Gson().fromJson(jsonString, JoinPlayerCommand.class);
    GameRecord gameRecord = Server.memoryGameDao.getGame(command.getGameID());
    ChessGame game = gameRecord.game();
    //before adding the user to the game, check if the game is full or invalid color.
    //if (gameRecord.)
    connections.addUser(command.getAuthString(), session, command.getGameID());
    var message = String.format("%s has joined the game as %s.", command.getUsername(), command.getPlayerColor());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    var loadGame=new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game, command.getPlayerColor());
    connections.broadcast(command.getAuthString(), command.getGameID(), notification);
    connections.rootBroadcast(command.getAuthString(), loadGame);
  }
  private void joinObserver(String jsonString, Session session) throws IOException, DataAccessException {
    JoinObserverCommand command = new Gson().fromJson(jsonString, JoinObserverCommand.class);
    ChessGame game = Server.memoryGameDao.getGame(command.getGameID()).game();
    connections.addUser(command.getAuthString(), session, command.getGameID());
    var message = String.format("%s is observing the game.", command.getUsername());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    var loadGame=new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game, ChessGame.TeamColor.WHITE);
    connections.broadcast(command.getAuthString(), command.getGameID(), notification);
    connections.rootBroadcast(command.getAuthString(), loadGame);
  }
  private void leave(String jsonString, Session session) throws IOException {
    LeaveCommand command = new Gson().fromJson(jsonString, LeaveCommand.class);
    var message = String.format("%s has left the game.", command.getUsername());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    connections.broadcast(command.getAuthString(), command.getGameID(), notification);
    connections.remove(command.getAuthString());
  }
  private void resign(String jsonString, Session session) throws IOException {
    ResignCommand command = new Gson().fromJson(jsonString, ResignCommand.class);
    var message = String.format("%s has forfeited the game.", command.getUsername());
    var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
    connections.broadcast(command.getAuthString(), command.getGameID(), notification);
    connections.remove(command.getAuthString());
  }
  private void makeMove(String jsonString, Session session) throws Exception {
    MakeMoveCommand command=new Gson().fromJson(jsonString, MakeMoveCommand.class);
    GameRecord gameRecord = Server.memoryGameDao.getGame(command.getGameID());
    ChessGame game = Server.memoryGameDao.getGame(command.getGameID()).game();
    try {
      game.makeMove(command.getMove());
      //checks if either color is in checkmate or stalemate and if so the game needs to end.
      if (game.isInCheckmate(ChessGame.TeamColor.WHITE) || game.isInCheckmate(ChessGame.TeamColor.BLACK)){
        var message=String.format("%s has won the game.", command.getUsername());
        var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast("", command.getGameID(), notification);
      }
      else if (game.isInStalemate(ChessGame.TeamColor.WHITE) || game.isInStalemate(ChessGame.TeamColor.BLACK)) {
        String message = "STALEMATE";
        var notification = new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast("", command.getGameID(), notification);
      }
      else if (game.isInCheck(ChessGame.TeamColor.WHITE) || game.isInCheck(ChessGame.TeamColor.BLACK)) {
        var message = "";
        if (game.isInCheck(ChessGame.TeamColor.WHITE)) {
          message=String.format("%s is in check.", gameRecord.whiteUsername());
        }
        else {
          message=String.format("%s is in check.", gameRecord.blackUsername());
        }
        var notification=new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
        connections.broadcast("", command.getGameID(), notification);
      }
      Server.memoryGameDao.updateGame(game, command.getGameID()); //update the database after making the move.
      var message=String.format("%s moved: %s.", command.getUsername(), command.getMoveString());
      var notification=new NotificationMessage(ServerMessage.ServerMessageType.NOTIFICATION, message);
      var loadGame=new LoadGameMessage(ServerMessage.ServerMessageType.LOAD_GAME, game, command.getColor());
      connections.broadcast("", command.getGameID(), loadGame);
      connections.broadcast(command.getAuthString(), command.getGameID(), notification);
    } catch (InvalidMoveException e) {
      var message=String.format("Invalid move: %s", e.getMessage());
      var error=new ErrorMessage(ServerMessage.ServerMessageType.ERROR, message);
      connections.broadcast("", command.getGameID(), error); //broadcasting to everyone, probably should only go to the player who made the move.
    }
  }
}
