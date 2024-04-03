package webSocketMessages.userCommands;

public class JoinPlayerCommand extends UserGameCommand {
//  public JoinPlayerCommand(String authToken, CommandType commandType){
//    super(authToken, commandType);
//  }
  int gameId;
  String playerColor;
  public JoinPlayerCommand(String authToken, CommandType type, int gameId, String playerColor) {
    super(authToken, type);
    this.gameId = gameId;
    this.playerColor = playerColor;
  }
}
