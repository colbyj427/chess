package dataAccess;

import chess.ChessGame;
import model.GameRecord;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDao implements GameDaoInterface{
  private Collection<GameRecord> gameDataArray = new ArrayList<>();
  public GameRecord createGame(String gameName) throws DataAccessException{
    int gameID = UniqueIDGenerator.generateUniqueID();
    GameRecord gameRecord = new GameRecord(gameID, "", "", gameName, new ChessGame(), new ArrayList<>());
    gameDataArray.add(gameRecord);
    return gameRecord;
  }

  public Collection<GameRecord> listGames() throws DataAccessException{
    return gameDataArray;
  }

  public void clear() {
    gameDataArray.clear();
  }
}
