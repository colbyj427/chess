package dataModels;

import chess.ChessGame;

public class AuthData {

  private String authToken;
  private String userName;

  public AuthData(final String authToken, final String userName) {
    this.authToken=authToken;
    this.userName = userName;
  }
  public String getAuthToken() {
    return authToken;
  }

  public void setAuthToken(String authToken) {
    this.authToken=authToken;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName=userName;
  }


}
