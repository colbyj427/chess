package dataAccess;

import model.GameRecord;

import java.util.Collection;

public class MySQLGameDao implements GameDaoInterface{
  public GameRecord createGame(String gameName) throws DataAccessException{
    .
  }

  public Collection<GameRecord> listGames() throws DataAccessException{
    .
  }

  public GameRecord getGame(int gameID) throws DataAccessException{
    .
  }

  public GameRecord addObserver(String username, GameRecord gameRecord) throws DataAccessException{
    .
  }

  public GameRecord addPlayer(String username, String playerColor, GameRecord gameRecord) throws DataAccessException{
    .
  }

  public void clear(){
    .
  }
}
