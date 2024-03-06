package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MySQLAuthDao;
import dataAccess.MySQLGameDao;
import dataAccess.MySQLUserDao;
import model.AuthRecord;
import model.GameRecord;
import model.UserRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class DataAccessTests {

//  MySQLUserDao Tests
  @Test
  public void SuccessfulAddUser() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();
    UserRecord newUser = new UserRecord("spongebob", "squarepants", "pineapple@gmail.com");

    Assertions.assertDoesNotThrow(() -> {
      mySQLUserDao.clear();
      mySQLUserDao.addUser(newUser.username(), newUser.password(), newUser.email());
    });
  }
  @Test
  public void BadAddUser() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();
    UserRecord newUser = new UserRecord("spongebob", "", "pineapple@gmail.com");

    Assertions.assertThrows((DataAccessException.class), () -> {
      mySQLUserDao.clear();
      mySQLUserDao.addUser(newUser.username(), newUser.password(), newUser.email());
    });
  }
  @Test
  public void SuccessfulGetUser() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();
    UserRecord newUser = new UserRecord("spongebob", "squarepants", "pineapple@gmail.com");
    Assertions.assertDoesNotThrow(() -> {
      mySQLUserDao.clear();
      mySQLUserDao.addUser(newUser.username(), newUser.password(), newUser.email());
      mySQLUserDao.getUser(newUser.username(), newUser.password());
    });
  }
  @Test
  public void BadGetUser() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();
    UserRecord newUser = new UserRecord("spongebob", "squarepants", "pineapple@gmail.com");

    Assertions.assertThrows((DataAccessException.class), () -> {
      mySQLUserDao.clear();
      mySQLUserDao.addUser(newUser.username(), newUser.password(), newUser.email());
      mySQLUserDao.getUser(newUser.username(), "incorrectPassword");
    });
  }

//  MySQLAuthDao Tests
  @Test
  public void SuccessfulAddAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLAuthDao.clear();
      mySQLAuthDao.addAuth("spongebob");
    });
  }
  @Test
  public void BadAddAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertThrows((DataAccessException.class), () -> {
      mySQLAuthDao.clear();
      mySQLAuthDao.addAuth("");
    });
  }
  @Test
  public void SuccessfulRemoveAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLAuthDao.clear();
      AuthRecord auth = mySQLAuthDao.addAuth("colby");
      mySQLAuthDao.removeAuth(auth.authToken());
    });
  }
  @Test
  public void BadRemoveAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertThrows((DataAccessException.class), () -> {
      mySQLAuthDao.clear();
      mySQLAuthDao.addAuth("colby");
      mySQLAuthDao.removeAuth("InvalidAuthToken");
    });
  }
  @Test
  public void SuccessfulGetAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLAuthDao.clear();
      AuthRecord authRecord = mySQLAuthDao.addAuth("spongebob");
      mySQLAuthDao.getAuth(authRecord.authToken());
    });
  }
  @Test
  public void BadGetAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertThrows((DataAccessException.class), () -> {
      mySQLAuthDao.clear();
      mySQLAuthDao.addAuth("spongebob");
      mySQLAuthDao.getAuth("InvalidAuthToken");
    });
  }


//  MySQLGameDao Tests
  @Test
  public void SuccessfulCreateGame() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLGameDao.clear();
      mySQLGameDao.createGame("aNewGame");
    });
  }
  @Test
  public void BadCreateGame() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertThrows((DataAccessException.class), () -> {
      mySQLGameDao.clear();
      mySQLGameDao.createGame("");
    });
  }
  @Test
  public void SuccessfulGetGame() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      mySQLGameDao.getGame(gameRecord.gameID());
    });
  }
  @Test
  public void BadGetGame() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertThrows((DataAccessException.class), () -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      mySQLGameDao.getGame(25);
    });
  }
  @Test
  public void SuccessfulAddObserver() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      mySQLGameDao.addObserver("justWatching", gameRecord);
      mySQLGameDao.addObserver("ilovechess", gameRecord);
    });
  }
  @Test
  public void BadAddObserver() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertThrows((DataAccessException.class), () -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      GameRecord badGameRecord = new GameRecord(gameRecord.gameID() + 100, gameRecord.whiteUsername(), gameRecord.blackUsername(), gameRecord.gameName(),
              gameRecord.game(), gameRecord.spectators());
      mySQLGameDao.addObserver("justWatching", badGameRecord);
    });
  }
  @Test
  public void SuccessfulAddPlayer() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      mySQLGameDao.addPlayer("imgonnawin", "WHITE" ,gameRecord);
      mySQLGameDao.addPlayer("ilovechess", "BLACK", gameRecord);
    });
  }
  @Test
  public void BadAddPlayer() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertThrows((DataAccessException.class), () -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      GameRecord badGameRecord = new GameRecord(gameRecord.gameID() + 100, gameRecord.whiteUsername(), gameRecord.blackUsername(), gameRecord.gameName(),
              gameRecord.game(), gameRecord.spectators());
      mySQLGameDao.addPlayer("imgonnawin", "WHITE" , badGameRecord);
      mySQLGameDao.addPlayer("ilovechess", "BLACK", badGameRecord);
    });
  }
  @Test
  public void SuccessfulListGames() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      GameRecord gameRecord2 = mySQLGameDao.createGame("anotherGame");
      Collection<GameRecord> games = mySQLGameDao.listGames();
    });
  }
  @Test
  public void MySQLSuccessfulClear() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();
    MySQLGameDao mySQLGameDao = new MySQLGameDao();
    Assertions.assertDoesNotThrow(() -> {
      mySQLUserDao.clear();
      mySQLAuthDao.clear();
      mySQLGameDao.clear();
    });
  }
}
