package webSocketMessages.userCommands;

public class LeaveCommand extends UserGameCommand {
  int gameId;
  public LeaveCommand(String authToken, CommandType type, int gameId) {
    super(authToken, type);
    this.gameId = gameId;
  }
}
