package webSocketMessages.serverMessages;

public class ErrorMessage extends ServerMessage {
  String errorMessage;
  public ErrorMessage(ServerMessageType type, String errorMessage) {
    super(type);
    this.errorMessage = errorMessage;
  }
}