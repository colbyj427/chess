package webSocketMessages.userCommands;

public class Join_ObserverCommand extends UserGameCommand {
  int gameId;
  public Join_ObserverCommand(String authToken, CommandType type, int gameId) {
    super(authToken, type);
    this.gameId = gameId;
  }
}
