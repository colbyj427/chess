package service;

import dataAccess.DataAccessException;
import model.AuthRecord;
import model.GameRecord;
import server.Server;

import javax.xml.crypto.Data;
import java.util.Collection;

public class GameService {
  public GameRecord createGame(String authToken, String gameName) throws DataAccessException {
    Server.memoryAuthDao.getAuth(authToken);
    return Server.memoryGameDao.createGame(gameName);
  }
  public Collection<GameRecord> listGames(String authToken) throws DataAccessException{
    Server.memoryAuthDao.getAuth(authToken);
    return Server.memoryGameDao.listGames();
  }
  public GameRecord joinGame(String authToken, String playerColor, int gameID) throws DataAccessException{
    AuthRecord authRecord = Server.memoryAuthDao.getAuth(authToken);
    GameRecord gameRecord = Server.memoryGameDao.getGame(gameID);
    //if no color is specified, the user joins as an observer
    if (playerColor == null) {
      return Server.memoryGameDao.addObserver(authRecord.username(), gameRecord);
    }
    //if a color is specified, add the caller as the requested color, otherwise throw 403, already taken
    else if (playerColor.equals("WHITE") | playerColor.equals("BLACK")) {
        return Server.memoryGameDao.addPlayer(authRecord.username(), playerColor, gameRecord);
      }
    throw new DataAccessException(400, "bad request");
  }
}
