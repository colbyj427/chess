package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinPlayerCommand extends UserGameCommand {
  int gameID;
  ChessGame.TeamColor playerColor;
  public JoinPlayerCommand(String authToken, String username, CommandType type, int gameId, ChessGame.TeamColor playerColor) {
    super(authToken, username, type);
    this.gameID= gameId;
    this.playerColor = playerColor;
  }
  public ChessGame.TeamColor getPlayerColor() {
    return playerColor;
  }
    public int getGameID() {
        return gameID;
    }
}
