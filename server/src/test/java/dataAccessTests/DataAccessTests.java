package dataAccessTests;

import dataAccess.MySQLUserDao;
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
      mySQLUserDao.addUser(newUser.username(), newUser.password(), newUser.email());
    });
  }
  @Test
  public void MySQLUserDaoSuccessfulGetUser() {
    MySQLUserDao mySQLUserDao = new MySQLUserDao();

    Assertions.assertDoesNotThrow(() -> {
      mySQLUserDao.getUser("username", "password");
    });
  }
}
