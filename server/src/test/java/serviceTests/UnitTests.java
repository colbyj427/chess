package serviceTests;

import dataAccess.*;
import model.AuthRecord;
import model.GameRecord;
import model.UserRecord;
import org.junit.jupiter.api.*;
import service.ClearService;
import service.UserService;

import java.util.ArrayList;

public class UnitTests {
  @Test
  public void clearServiceTest() {
    MemoryUserDao memoryUserDao = new MemoryUserDao();
    MemoryAuthDao memoryAuthDao = new MemoryAuthDao();
    MemoryGameDao memoryGameDao = new MemoryGameDao();

    ClearService clearService = new ClearService();
    Assertions.assertDoesNotThrow(() -> {
      memoryUserDao.addUser("username", "password", "email");
      memoryAuthDao.addAuth("username");
      memoryGameDao.createGame("1");

      clearService.clear();
    });
  }
  @Test
  public void userServiceSuccessfulRegister() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();
    Assertions.assertDoesNotThrow(() -> {
      userService.register(newUser.username(), newUser.password(), newUser.email());
    });
  }
  @Test
  public void userServiceBadRegister() {
    UserRecord newUser = new UserRecord("username", "", "email@gmail.com");
    UserService userService = new UserService();
    Assertions.assertThrows((DataAccessException.class), () -> {
      userService.register(newUser.username(), newUser.password(), newUser.email());
    });
  }
  @Test
  public void userServiceSuccessfulLogin() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();

    Assertions.assertDoesNotThrow(() -> {
      userService.register(newUser.username(), newUser.password(), newUser.email());
      userService.login(newUser.username(), newUser.password());
    });
  }
  @Test
  public void userServiceBadLogin() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();

    Assertions.assertThrows(DataAccessException.class, () -> {
      userService.register(newUser.username(), newUser.password(), newUser.email());
      userService.login(newUser.username(), "wrongPassword");
    });
  }
  @Test
  public void userServiceSuccessfulLogout() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();

    Assertions.assertDoesNotThrow(() -> {
      AuthRecord authRecord = userService.register(newUser.username(), newUser.password(), newUser.email());
      userService.logout(authRecord.authToken());
    });
  }
  @Test
  public void userServiceBadLogout() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();

    Assertions.assertThrows(DataAccessException.class, () -> {
      AuthRecord authRecord = userService.register(newUser.username(), newUser.password(), newUser.email());
      userService.logout("InvalidAuthToken");
    });
  }
}
