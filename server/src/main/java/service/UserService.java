package service;

import dataAccess.DataAccessException;
import model.AuthRecord;
import model.UserRecord;
import server.Server;

public class UserService {
  public AuthRecord register(String username, String password, String email) throws DataAccessException {
      //add the user
      Server.memoryUserDao.addUser(username, password, email);
      //create an authRecord and return it
      return Server.memoryAuthDao.addAuth(username);
  }
}
