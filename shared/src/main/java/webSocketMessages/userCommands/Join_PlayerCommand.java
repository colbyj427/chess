package webSocketMessages.userCommands;

public class Join_PlayerCommand extends UserGameCommand {
  int gameId;
  String playerColor;
  public Join_PlayerCommand(String authToken, CommandType type, int gameId, String playerColor) {
    super(authToken, type);
    this.gameId = gameId;
    this.playerColor = playerColor;
  }
}
