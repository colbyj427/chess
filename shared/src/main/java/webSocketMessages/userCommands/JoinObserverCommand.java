package webSocketMessages.userCommands;

public class JoinObserverCommand extends UserGameCommand {
  int gameID;
  public JoinObserverCommand(String authToken, String username, CommandType type, int gameId) {
    super(authToken, username, type);
    this.gameID= gameId;
  }
    public int getGameID() {
        return gameID;
    }
}
