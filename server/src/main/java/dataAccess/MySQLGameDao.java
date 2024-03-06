package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthRecord;
import model.GameRecord;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLGameDao implements GameDaoInterface{
  public GameRecord createGame(String gameName) throws DataAccessException{
    int gameID = UniqueIDGenerator.generateUniqueID();
    ChessGame chessGame = new ChessGame();
    ArrayList<String> spectators = new ArrayList<>();
    var serializer = new Gson();
    String serializedGame = serializer.toJson(chessGame);
    String serializedSpectators = serializer.toJson(spectators);
    GameRecord gameRecord = new GameRecord(gameID, null, null, gameName, chessGame, spectators);

    var statement="INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, game, spectators) VALUES ( ?, ?, ?, ?, ?, ?)";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.setInt(1, gameID);
      preparedStatement.setString(2, null);
      preparedStatement.setString(3, null);
      preparedStatement.setString(4, gameName);
      preparedStatement.setString(5, serializedGame);
      preparedStatement.setString(6, serializedSpectators);
      preparedStatement.executeUpdate();
      return gameRecord;
    } catch (Exception dataAccessException) {
      throw new DataAccessException(500, dataAccessException.getMessage());
    }
  }
  public GameRecord getGame(int gameID) throws DataAccessException{
    var statement="SELECT * FROM games WHERE gameID = ?;";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.setInt(1, gameID);
      ResultSet rs = preparedStatement.executeQuery();
      rs.next();
      return readGame(rs);
    } catch (Exception dataAccessException) {
      throw new DataAccessException(400, "bad request");
    }
  }
  public Collection<GameRecord> listGames() throws DataAccessException{
    Collection<GameRecord> allGames = new ArrayList<>();
    var statement="SELECT * FROM games;";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        allGames.add(readGame(rs));
      }
      return allGames;
    } catch (Exception dataAccessException) {
      throw new DataAccessException(500, dataAccessException.getMessage());
    }
  }
  public GameRecord addObserver(String username, GameRecord gameRecord) throws DataAccessException{
    ArrayList<String> newSpectators = new ArrayList<>(gameRecord.spectators().size());

    if (!gameRecord.spectators().isEmpty()) {
      newSpectators.addAll(gameRecord.spectators());
    }
    newSpectators.add(username);
    GameRecord newGameRecord = new GameRecord(gameRecord.gameID(), gameRecord.whiteUsername(),
            gameRecord.blackUsername(), gameRecord.gameName(), gameRecord.game(), newSpectators);
    var serializer = new Gson();
    String serializedSpectators = serializer.toJson(newSpectators);

    var statement = "UPDATE games SET spectators = ? WHERE gameID = ?;";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.setString(1, serializedSpectators);
      preparedStatement.setInt(2, gameRecord.gameID());
      int numAffected = preparedStatement.executeUpdate();
      if (numAffected == 0) {
        throw new DataAccessException(400, "bad request");
      }
      return newGameRecord;
    } catch (Exception dataAccessException) {
      throw new DataAccessException(400, "bad request");
    }
  }
  public GameRecord addPlayer(String username, String playerColor, GameRecord gameRecord) throws DataAccessException {
    if (playerColor.equals("WHITE") & gameRecord.whiteUsername() == null) {
      GameRecord newGameRecord=new GameRecord(gameRecord.gameID(), username, gameRecord.blackUsername(),
              gameRecord.gameName(), gameRecord.game(), gameRecord.spectators());
      var statement="UPDATE games SET whiteUsername = ? WHERE gameID = ?;";
      try {
        var conn=DatabaseManager.getConnection();
        var preparedStatement=conn.prepareStatement(statement);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, gameRecord.gameID());
        int numAffected=preparedStatement.executeUpdate();
        if (numAffected == 0) {
          throw new DataAccessException(400, "bad request");
        }
        return newGameRecord;
      } catch (Exception e) {
        throw new DataAccessException(400, "bad request");
      }
    } else if (playerColor.equals("BLACK") & gameRecord.blackUsername() == null) {
      GameRecord newGameRecord=new GameRecord(gameRecord.gameID(), gameRecord.whiteUsername(), username,
              gameRecord.gameName(), gameRecord.game(), gameRecord.spectators());
      var statement="UPDATE games SET blackUsername = ? WHERE gameID = ?;";
      try {
        var conn=DatabaseManager.getConnection();
        var preparedStatement=conn.prepareStatement(statement);
        preparedStatement.setString(1, username);
        preparedStatement.setInt(2, gameRecord.gameID());
        int numAffected=preparedStatement.executeUpdate();
        if (numAffected == 0) {
          throw new DataAccessException(400, "bad request");
        }
        return newGameRecord;
      } catch (Exception e) {
        throw new DataAccessException(400, "bad request");
      }
    }
    throw new DataAccessException(403, "already taken");
  }
  public void clear() throws DataAccessException {
    var statement="DELETE FROM games;";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.executeUpdate();
    } catch (Exception dataAccessException) {
      throw new DataAccessException(500, dataAccessException.getMessage());
    }
  }
  private GameRecord readGame(ResultSet rs) throws Exception {
    int newGameID = rs.getInt("gameID");
    String newWhiteUsername = rs.getString("whiteUsername");
    String newBlackUsername = rs.getString("blackUsername");
    String newGameName = rs.getString("gameName");
    String jsonGame = rs.getString("game");
    String jsonSpectators = rs.getString("spectators");

    var serializer = new Gson();
    ChessGame newGame = serializer.fromJson(jsonGame, ChessGame.class);
    ArrayList<String> newSpectators = serializer.fromJson(jsonSpectators, ArrayList.class);
    return new GameRecord(newGameID, newWhiteUsername, newBlackUsername, newGameName, newGame, newSpectators);
  }
}
