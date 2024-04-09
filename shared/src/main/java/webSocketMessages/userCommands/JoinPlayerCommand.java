package webSocketMessages.userCommands;

public class JoinPlayerCommand extends UserGameCommand {
//  public JoinPlayerCommand(String authToken, CommandType commandType){
//    super(authToken, commandType);
//  }
  int gameId;
  String playerColor;
  public JoinPlayerCommand(String authToken, String username, CommandType type, int gameId, String playerColor) {
    super(authToken, username, type);
    this.gameId = gameId;
    this.playerColor = playerColor;
  }
  public String getPlayerColor() {
    return playerColor;
  }
    public int getGameId() {
        return gameId;
    }
}
