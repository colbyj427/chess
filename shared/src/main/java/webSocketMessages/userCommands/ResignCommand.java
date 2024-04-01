package webSocketMessages.userCommands;

public class ResignCommand extends UserGameCommand {
  int gameId;
  public ResignCommand(String authToken, CommandType type, int gameId) {
    super(authToken, type);
    this.gameId = gameId;
  }
}
