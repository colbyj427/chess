package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
  int gameId;
  ChessMove move;
  String moveString;
  String color;
  public MakeMoveCommand(String authToken, String username, CommandType type, int gameId, String color, ChessMove move, String moveString) {
    super(authToken, username, type);
    this.gameId = gameId;
    this.move = move;
    this.moveString = moveString;
    this.color = color;
  }

  public int getGameId() {
    return gameId;
  }

  public java.lang.String getColor() {
    return color;
  }

  public String getMoveString() {
    return moveString;
  }

  public ChessMove getMove() {
    return move;
  }
}
