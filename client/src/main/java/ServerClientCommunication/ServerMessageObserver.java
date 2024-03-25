package ServerClientCommunication;

import webSocketMessages.serverMessages.ServerMessage;

public interface ServerMessageObserver {
  ServerMessage notify (ServerMessage message);
}
