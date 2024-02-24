package serviceTests;

import dataAccess.MemoryAuthDao;
import dataAccess.MemoryGameDao;
import dataAccess.MemoryUserDao;
import model.AuthRecord;
import model.GameRecord;
import model.UserRecord;
import org.junit.jupiter.api.*;
import service.ClearService;

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

      clearService.clear();
    });
  }
}
