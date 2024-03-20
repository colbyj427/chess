package serviceTests;

import dataAccess.*;
import model.AuthRecord;
import model.GameRecord;
import model.UserRecord;
import org.junit.jupiter.api.*;
import service.ClearService;
import service.GameService;
import service.UserService;


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
    ClearService clearService = new ClearService();
    Assertions.assertDoesNotThrow(() -> {
      clearService.clear();
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
    ClearService clearService = new ClearService();

    Assertions.assertDoesNotThrow(() -> {
      clearService.clear();
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
  @Test
  public void gameServiceSuccessfulCreateGame() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();
    GameService gameService = new GameService();
    ClearService clearService = new ClearService();

    Assertions.assertDoesNotThrow(() -> {
      clearService.clear();
      AuthRecord authRecord = userService.register(newUser.username(), newUser.password(), newUser.email());
      gameService.createGame(authRecord.authToken(), "1");
    });
  }
  @Test
  public void gameServiceBadCreateGame() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();
    GameService gameService = new GameService();

    Assertions.assertThrows(DataAccessException.class, () -> {
      AuthRecord authRecord = userService.register(newUser.username(), newUser.password(), newUser.email());
      gameService.createGame("InvalidAuthToken", "1");
    });
  }
  @Test
  public void gameServiceSuccessfulListGames() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();
    GameService gameService = new GameService();
    ClearService clearService = new ClearService();

    Assertions.assertDoesNotThrow(() -> {
      clearService.clear();
      AuthRecord authRecord = userService.register(newUser.username(), newUser.password(), newUser.email());
      gameService.createGame(authRecord.authToken(), "1");
      gameService.listGames(authRecord.authToken());
    });
  }
  @Test
  public void gameServiceBadListGames() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();
    GameService gameService = new GameService();

    Assertions.assertThrows(DataAccessException.class, () -> {
      AuthRecord authRecord = userService.register(newUser.username(), newUser.password(), newUser.email());
      gameService.createGame(authRecord.authToken(), "1");
      gameService.listGames("InvalidAuthToken");
    });
  }
  @Test
  public void gameServiceSuccessfulJoinGame() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();
    GameService gameService = new GameService();
    ClearService clearService = new ClearService();

    Assertions.assertDoesNotThrow(() -> {
      clearService.clear();
      AuthRecord authRecord = userService.register(newUser.username(), newUser.password(), newUser.email());
      GameRecord gameRecord = gameService.createGame(authRecord.authToken(), "1");
      gameService.joinGame(authRecord.authToken(), "WHITE", gameRecord.gameID());
    });
  }
  @Test
  public void gameServiceBadJoinGame() {
    UserRecord newUser = new UserRecord("username", "password", "email@gmail.com");
    UserService userService = new UserService();
    GameService gameService = new GameService();

    Assertions.assertThrows(DataAccessException.class, () -> {
      AuthRecord authRecord = userService.register(newUser.username(), newUser.password(), newUser.email());
      gameService.createGame(authRecord.authToken(), "1");
      gameService.joinGame(authRecord.authToken(), "WHITE", 2);
    });
  }
}
