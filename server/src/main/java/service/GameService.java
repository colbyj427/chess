package service;

import dataAccess.DataAccessException;
import model.GameRecord;
import server.Server;

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
}
