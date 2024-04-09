package webSocketMessages.userCommands;

public class ResignCommand extends UserGameCommand {
  int gameID;
  public ResignCommand(String authToken, String username, CommandType type, int gameId) {
    super(authToken, username, type);
    this.gameID= gameId;
  }

  public int getGameID() {
    return gameID;
  }
}
