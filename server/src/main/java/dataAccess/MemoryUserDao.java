package dataAccess;

import model.UserRecord;

import java.util.Collection;

public class MemoryUserDao implements UserDaoInterface{
  private Collection<UserRecord> userDataArray;

  public void clear() {
    userDataArray.clear();
  }
}
