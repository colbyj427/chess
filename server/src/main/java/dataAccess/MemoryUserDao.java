package dataAccess;

import model.UserRecord;
import model.AuthRecord;
import org.eclipse.jetty.server.Authentication;

import java.util.Collection;

public class MemoryUserDao implements UserDaoInterface{
  private Collection<UserRecord> userDataArray;

  public UserRecord getUser(String username, String password, String email) throws DataAccessException{
    for (UserRecord user: userDataArray) {
      if (user.username().equals(username)){
        return user;
      }
    throw new DataAccessException("User not found.");
    }
    return null;
  }

  public UserRecord addUser(String username, String password, String email) throws DataAccessException{
    for (UserRecord user: userDataArray) {
      if (user.username().equals(username) && user.password().equals(password)) {
        throw new DataAccessException("User already exists.");
      }
    }
    UserRecord newUser = new UserRecord(username, password, email);
    userDataArray.add(newUser);
    return newUser;
  }

  public void clear() {
    userDataArray.clear();
  }
}
