package dataAccess;

import model.AuthRecord;

import java.util.Collection;

public class MemoryAuthDao implements AuthDaoInterface{
  private Collection<AuthRecord> authDataArray;

  public void clear(){
    authDataArray.clear();
  }
}
