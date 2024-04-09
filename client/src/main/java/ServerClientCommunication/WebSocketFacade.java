package ServerClientCommunication;

import chess.ChessGame;
import chess.ChessMove;
import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketFacade extends Endpoint {

  Session session;
  ServerMessageObserver notificationHandler;


  public WebSocketFacade(String url, ServerMessageObserver notificationHandler) throws Exception {
    try {
      url = url.replace("http", "ws");
      URI socketURI = new URI(url + "/connect");
      this.notificationHandler = notificationHandler;
      WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      //getting an error thrown on this line.
      this.session = container.connectToServer(this, socketURI);
      //set message handler
      this.session.addMessageHandler(new MessageHandler.Whole<String>() {
        @Override
        public void onMessage(String message) {
          notificationHandler.notify(message);
        }
      });
    } catch (DeploymentException | IOException | URISyntaxException ex) {
      throw new Exception(ex.getMessage());
    }
  }
  //Endpoint requires this method, but you don't have to do anything
  @Override
  public void onOpen(Session session, EndpointConfig endpointConfig) {
  }
  public void joinPlayer(String authToken, String username, ChessGame.TeamColor team, int gameId) throws Exception {
    try {
      var notification = new JoinPlayerCommand(authToken, username, UserGameCommand.CommandType.JOIN_PLAYER, gameId, team);
      this.session.getBasicRemote().sendText(new Gson().toJson(notification));
    } catch (IOException e) {
      throw new Exception(e.getMessage());
    }
  }
  public void joinObserver(String authToken, String username, int gameId) throws Exception {
    try {
      var notification = new JoinObserverCommand(authToken, username, UserGameCommand.CommandType.JOIN_OBSERVER, gameId);
      this.session.getBasicRemote().sendText(new Gson().toJson(notification));
    } catch (IOException e) {
      throw new Exception(e.getMessage());
    }
  }
  public void leave(String authToken, String username, int gameId) throws Exception {
    try {
      var notification = new LeaveCommand(authToken, username, UserGameCommand.CommandType.LEAVE, gameId);
      this.session.getBasicRemote().sendText(new Gson().toJson(notification));
      this.session.close();
    } catch (IOException e) {
      throw new Exception(e.getMessage());
    }
  }
  public void resign(String authToken, String username, int gameId) throws Exception {
    try {
      var notification = new ResignCommand(authToken, username, UserGameCommand.CommandType.RESIGN, gameId);
      this.session.getBasicRemote().sendText(new Gson().toJson(notification));
      this.session.close();
    } catch (IOException e) {
      throw new Exception(e.getMessage());
    }
  }
  public void makeMove(String authToken, String username, int gameId, ChessGame.TeamColor color, ChessMove move, String moveString) throws Exception {
    try {
      var notification = new MakeMoveCommand(authToken, username, UserGameCommand.CommandType.MAKE_MOVE, gameId, color, move, moveString);
      this.session.getBasicRemote().sendText(new Gson().toJson(notification));
    } catch (IOException e) {
      throw new Exception(e.getMessage());
    }
  }
}