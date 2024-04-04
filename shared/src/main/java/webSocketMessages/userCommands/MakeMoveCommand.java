package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
  int gameId;
  ChessMove move;
  public MakeMoveCommand(String authToken, String username, CommandType type, int gameId, ChessMove move) {
    super(authToken, username, type);
    this.gameId = gameId;
    this.move = move;
  }
}
