package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {
  ChessGame game;
  ChessGame.TeamColor color;
  public LoadGameMessage(ServerMessageType serverMessageType, ChessGame game, ChessGame.TeamColor color) {
    super(serverMessageType);
    this.game = game;
    this.color = color;
  }
  public ChessGame getGame() {
    return game;
  }
  public ChessGame.TeamColor getColor() {
    return color;
  }
}
