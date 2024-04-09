package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
  ChessGame game;
  String color;
  public LoadGameMessage(ServerMessageType serverMessageType, ChessGame game, String color) {
    super(serverMessageType);
    this.game = game;
    this.color = color;
  }
  public ChessGame getGame() {
    return game;
  }
  public String getColor() {
    return color;
  }
}
