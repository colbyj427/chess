package webSocketMessages.userCommands;

public class ResignCommand extends UserGameCommand {
  int gameId;
  public ResignCommand(String authToken, String username, CommandType type, int gameId) {
    super(authToken, username, type);
    this.gameId = gameId;
  }
}
