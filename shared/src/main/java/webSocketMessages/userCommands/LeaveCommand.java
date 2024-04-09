package webSocketMessages.userCommands;

public class LeaveCommand extends UserGameCommand {
  int gameID;
  public LeaveCommand(String authToken, String username, CommandType type, int gameId) {
    super(authToken, username, type);
    this.gameID= gameId;
  }

  public int getGameID() {
    return gameID;
  }
}
