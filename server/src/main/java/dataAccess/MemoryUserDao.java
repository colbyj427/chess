package dataAccess;

import model.UserRecord;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryUserDao implements UserDaoInterface{
  private Collection<UserRecord> userDataArray = new ArrayList<>();

  public UserRecord getUser(String username, String password) throws DataAccessException{
    for (UserRecord user: userDataArray) {
      if (user.username().equals(username) && user.password().equals(password)) {
        return user;
      }
    }
    throw new DataAccessException(401, "unauthorized");
  }

  public UserRecord addUser(String username, String password, String email) throws DataAccessException{
    for (UserRecord user: userDataArray) {
      if (user.username().equals(username) && user.password().equals(password)) {
        throw new DataAccessException(403, "already taken");
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
