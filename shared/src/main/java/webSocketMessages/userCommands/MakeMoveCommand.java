package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
  int gameID;
  ChessMove move;
  String moveString;
  ChessGame.TeamColor color;
  public MakeMoveCommand(String authToken, String username, CommandType type, int gameId, ChessGame.TeamColor color, ChessMove move, String moveString) {
    super(authToken, username, type);
    this.gameID= gameId;
    this.move = move;
    this.moveString = moveString;
    this.color = color;
  }

  public int getGameID() {
    return gameID;
  }

  public ChessGame.TeamColor getColor() {
    return color;
  }

  public String getMoveString() {
    return moveString;
  }

  public ChessMove getMove() {
    return move;
  }
}
