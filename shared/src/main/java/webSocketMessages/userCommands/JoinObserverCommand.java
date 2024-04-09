package webSocketMessages.userCommands;

public class JoinObserverCommand extends UserGameCommand {
  int gameId;
  public JoinObserverCommand(String authToken, String username, CommandType type, int gameId) {
    super(authToken, username, type);
    this.gameId = gameId;
  }
}
