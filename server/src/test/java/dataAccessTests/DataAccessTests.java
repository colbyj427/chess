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

public class DataAccessTests {
  @Test
  public void MySQLUserDaoSuccessfulAddUser() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();
    UserRecord newUser = new UserRecord("spongebob", "squarepants", "pineapple@gmail.com");

    Assertions.assertDoesNotThrow(() -> {
      mySQLUserDao.clear();
      mySQLUserDao.addUser(newUser.username(), newUser.password(), newUser.email());
    });
  }
  @Test
  public void MySQLUserDaoSuccessfulGetUser() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();
    UserRecord newUser = new UserRecord("spongebob", "squarepants", "pineapple@gmail.com");
    Assertions.assertDoesNotThrow(() -> {
      mySQLUserDao.clear();
      mySQLUserDao.addUser(newUser.username(), newUser.password(), newUser.email());
      UserRecord userRecord = mySQLUserDao.getUser(newUser.username(), newUser.password());
    });
  }
  @Test
  public void MySQLUserDaoSuccessfulAddAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLAuthDao.clear();
      mySQLAuthDao.addAuth("spongebob");
    });
  }
  @Test
  public void MySQLUserDaoSuccessfulRemoveAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLAuthDao.clear();
      AuthRecord auth = mySQLAuthDao.addAuth("colby");
      mySQLAuthDao.removeAuth(auth.authToken());
    });
  }
  @Test
  public void MySQLUserDaoSuccessfulGetAuth() {
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLAuthDao.clear();
      AuthRecord authRecord = mySQLAuthDao.addAuth("spongebob");
      mySQLAuthDao.getAuth(authRecord.authToken());
    });
  }
  @Test
  public void MySQLGameDaoSuccessfulCreateGame() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLGameDao.clear();
      mySQLGameDao.createGame("aNewGame");
    });
  }
  @Test
  public void MySQLGameDaoSuccessfulGetGame() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      mySQLGameDao.getGame(gameRecord.gameID());
    });
  }
  @Test
  public void MySQLGameDaoSuccessfulAddObserver() {
    MySQLGameDao mySQLGameDao = new MySQLGameDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLGameDao.clear();
      GameRecord gameRecord = mySQLGameDao.createGame("aNewGame");
      mySQLGameDao.addObserver("justWatching", gameRecord);
      mySQLGameDao.addObserver("ilovechess", gameRecord);
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
