package dataAccess;

import model.GameRecord;

import java.util.Collection;

public interface GameDaoInterface {
  GameRecord createGame(String gameName) throws DataAccessException;
  Collection<GameRecord> listGames() throws DataAccessException;
  public GameRecord getGame(int gameID) throws DataAccessException;
  void clear();

  public GameRecord addObserver(String username, GameRecord gameRecord) throws DataAccessException;

  public GameRecord addPlayer(String username, String playerColor, GameRecord gameRecord) throws DataAccessException;
}
