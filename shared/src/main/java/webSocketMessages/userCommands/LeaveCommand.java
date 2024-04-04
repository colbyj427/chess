package webSocketMessages.userCommands;

public class LeaveCommand extends UserGameCommand {
  int gameId;
  public LeaveCommand(String authToken, String username, CommandType type, int gameId) {
    super(authToken, username, type);
    this.gameId = gameId;
  }
}
