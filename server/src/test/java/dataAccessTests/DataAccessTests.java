package dataAccessTests;

import dataAccess.MySQLAuthDao;
import dataAccess.MySQLUserDao;
import model.AuthRecord;
import model.UserRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
  public void MySQLSuccessfulClear() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();
    MySQLAuthDao mySQLAuthDao = new MySQLAuthDao();
    Assertions.assertDoesNotThrow(() -> {
      mySQLUserDao.clear();
      mySQLAuthDao.clear();
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
}
