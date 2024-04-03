package webSocketMessages.userCommands;

public class JoinObserverCommand extends UserGameCommand {
  int gameId;
  public JoinObserverCommand(String authToken, CommandType type, int gameId) {
    super(authToken, type);
    this.gameId = gameId;
  }
}
