package dataAccessTests;

import dataAccess.MySQLAuthDao;
import dataAccess.MySQLGameDao;
import dataAccess.MySQLUserDao;
import model.AuthRecord;
import model.GameRecord;
import model.UserRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.ClearService;
import service.GameService;
import service.UserService;

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
  public void SuccessfulGetUser() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();
    UserRecord newUser = new UserRecord("spongebob", "squarepants", "pineapple@gmail.com");
    Assertions.assertDoesNotThrow(() -> {
      mySQLUserDao.clear();
      mySQLUserDao.addUser(newUser.username(), newUser.password(), newUser.email());
      UserRecord userRecord = mySQLUserDao.getUser(newUser.username(), newUser.password());
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
  public void SuccessfulRemoveAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLAuthDao.clear();
      AuthRecord auth = mySQLAuthDao.addAuth("colby");
      mySQLAuthDao.removeAuth(auth.authToken());
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
  public void SuccessfulGetGame() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      mySQLGameDao.getGame(gameRecord.gameID());
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
