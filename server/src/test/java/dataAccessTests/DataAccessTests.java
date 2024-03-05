package dataAccessTests;

import model.UserRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.UserService;

public class DataAccessTests {
  @Test
  public void MySQLUserDaoSuccessfulLogin() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");

    Assertions.assertDoesNotThrow(() -> {
      userService.register(newUser.username(), newUser.password(), newUser.email());
      userService.login(newUser.username(), newUser.password());
    });
  }
}
