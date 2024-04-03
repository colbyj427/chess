package ServerClientCommunication;

import webSocketMessages.serverMessages.ServerMessage;

public interface ServerMessageObserver {
  void notify (String message); //this notify method was taking a ServerMessage as an argument before i tampered with it.
}
