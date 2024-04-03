package webSocketMessages.userCommands;

public class JoinPlayerCommand extends UserGameCommand {
  public JoinPlayerCommand(String authToken, CommandType commandType){
    super(authToken, commandType);
  }
//  int gameId;
//  String playerColor;
//  CommandType type;
//  public JoinPlayerCommand(String authToken, CommandType type, int gameId, String playerColor) {
//    super(authToken, type);
//    this.type = type;
//    this.gameId = gameId;
//    this.playerColor = playerColor;
//  }
}
