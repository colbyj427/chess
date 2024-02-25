package dataAccess;

import model.GameRecord;
import service.GameService;

import java.util.Collection;

public interface GameDaoInterface {
  GameRecord createGame(String gameName) throws DataAccessException;
  Collection<GameRecord> listGames() throws DataAccessException;
  void clear();
}
