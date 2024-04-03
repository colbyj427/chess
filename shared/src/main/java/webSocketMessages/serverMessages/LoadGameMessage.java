package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
  ChessGame game;
  String color;
  public LoadGameMessage(ServerMessageType type, ChessGame game, String color) {
    super(type);
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
