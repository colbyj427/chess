package dataModels;

import chess.ChessGame;

public class AuthData {

  private String authToken;
  private String userName;


  public AuthData(final String authToken, final String userName) {
    this.authToken=authToken;
    this.userName = userName;
  }
}
