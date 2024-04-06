package service;

import dataAccess.DataAccessException;
import model.AuthRecord;
import server.Server;

public class UserService {
  public AuthRecord register(String username, String password, String email) throws DataAccessException {
    //add the user
    //checks if any field is null or empty
    if (username == null | password == null | email == null){
      throw new DataAccessException(400, "bad request");
    }
    if (username.isEmpty() | password.isEmpty() | email.isEmpty()){
      throw new DataAccessException(400, "bad request");
    }
    Server.memoryUserDao.addUser(username, password, email);
    //create an authRecord and return it
    return Server.memoryAuthDao.addAuth(username);
  }
  public AuthRecord login(String username, String password) throws DataAccessException {
    Server.memoryUserDao.getUser(username, password);
    return Server.memoryAuthDao.addAuth(username);
  }
  public void logout(String authToken) throws DataAccessException {
    Server.memoryAuthDao.removeAuth(authToken);
  }
}
