package dataModels;

import chess.ChessGame;

public class GameData {
  private int gameID;
  private String whiteUsername;
  private String blackUsername;
  private String gameName;
  private ChessGame game;

  public GameData(final int gameID, final String whiteUsername, final String blackUsername, final String gameName,
                  ChessGame game) {
    this.gameID = gameID;
    this.whiteUsername = whiteUsername;
    this.blackUsername = blackUsername;
    this.gameName = gameName;
    this.game = game;
  }
}
