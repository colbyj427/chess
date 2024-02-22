package dataModels;

import chess.ChessPosition;

public class UserData {
  private String username;
  private String password;

  public UserData(final String username, final String password) {
    this.username = username;
    this.password = password;
  }
}
