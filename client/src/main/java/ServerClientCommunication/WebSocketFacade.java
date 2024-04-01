package ServerClientCommunication;

import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.LeaveCommand;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

//need to extend Endpoint for websocket to work properly
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
          ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
          notificationHandler.notify(notification);
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

  public void leave(String authToken, int gameId) throws Exception {
    try {
      var notification=new LeaveCommand(authToken, UserGameCommand.CommandType.LEAVE, gameId);
      this.session.getBasicRemote().sendText(new Gson().toJson(notification));
    } catch (IOException e) {
      throw new Exception(e.getMessage());
    }
  }

//  public void enterPetShop(String visitorName) throws ResponseException {
//    try {
//      var action = new Action(Action.Type.ENTER, visitorName);
//      this.session.getBasicRemote().sendText(new Gson().toJson(action));
//    } catch (IOException ex) {
//      throw new ResponseException(500, ex.getMessage());
//    }
//  }
//  public void leavePetShop(String visitorName) throws ResponseException {
//    try {
//      var action = new Action(Action.Type.EXIT, visitorName);
//      this.session.getBasicRemote().sendText(new Gson().toJson(action));
//      this.session.close();
//    } catch (IOException ex) {
//      throw new ResponseException(500, ex.getMessage());
//    }
//  }

}