package dataAccess;

import chess.ChessGame;
import model.GameRecord;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDao implements GameDaoInterface{
  private Collection<GameRecord> gameDataArray = new ArrayList<>();
  public GameRecord createGame(String gameName) throws DataAccessException{
    int gameID = UniqueIDGenerator.generateUniqueID();
    GameRecord gameRecord = new GameRecord(gameID, null, null, gameName, new ChessGame(), new ArrayList<>());
    gameDataArray.add(gameRecord);
    return gameRecord;
  }

  public GameRecord getGame(int gameID) throws DataAccessException{
    for (GameRecord record: gameDataArray) {
      if (record.gameID() == gameID) {
        return record;
      }
    }
    throw new DataAccessException(400, "bad request");
  }

  public Collection<GameRecord> listGames() throws DataAccessException{
    return gameDataArray;
  }
  public GameRecord addObserver(final String username, final GameRecord gameRecord) throws DataAccessException {
    ArrayList<String> newSpectators = new ArrayList<>(gameRecord.spectators().size());

    for (int i = 0; i < gameRecord.spectators().size(); i++) {
      newSpectators.set(i, gameRecord.spectators().get(i));
    }
    newSpectators.add(username);
    GameRecord newGameRecord = new GameRecord(gameRecord.gameID(), gameRecord.whiteUsername(),
            gameRecord.blackUsername(), gameRecord.gameName(), gameRecord.game(), newSpectators);
    gameDataArray.remove(gameRecord);
    gameDataArray.add(newGameRecord);
    return newGameRecord;
  }

  public GameRecord addPlayer(final String username, final String playerColor, final GameRecord gameRecord) throws DataAccessException{
    //create a new gameRecord with the player in it and delete the old one.
    if (playerColor.equals("WHITE") & gameRecord.whiteUsername() == null) {
      GameRecord newGameRecord = new GameRecord(gameRecord.gameID(), username, gameRecord.blackUsername(),
              gameRecord.gameName(), gameRecord.game(), gameRecord.spectators());
      gameDataArray.remove(gameRecord);
      gameDataArray.add(newGameRecord);
      return newGameRecord;
    }
    else if (playerColor.equals("BLACK") & gameRecord.blackUsername() == null) {
      GameRecord newGameRecord = new GameRecord(gameRecord.gameID(), gameRecord.whiteUsername(), username,
              gameRecord.gameName(), gameRecord.game(), gameRecord.spectators());
      gameDataArray.remove(gameRecord);
      gameDataArray.add(newGameRecord);
      return newGameRecord;
    }
    throw new DataAccessException(403, "already taken");
  }
  public void clear() {
    gameDataArray.clear();
  }
}
