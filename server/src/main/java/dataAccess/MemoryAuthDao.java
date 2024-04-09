package dataAccess;

import model.AuthRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class MemoryAuthDao implements AuthDaoInterface{
  private Collection<AuthRecord> authDataArray = new ArrayList<>();

  public AuthRecord addAuth(String username) throws DataAccessException{
    String authToken = UUID.randomUUID().toString();
    AuthRecord auth = new AuthRecord(authToken, username);
    authDataArray.add(auth);
    return auth;
  }
  public AuthRecord getAuth(String authToken) throws DataAccessException{
    for (AuthRecord record: authDataArray){
      if (record.authToken().equals(authToken)) {
        return record;
      }
    }
    throw new DataAccessException(401, "unauthorized");
  }
  public void removeAuth(String authToken) throws DataAccessException{
    for (AuthRecord record: authDataArray){
      if (record.authToken().equals(authToken)) {
        authDataArray.remove(record);
        return;
      }
    }
    //if authToken doesn't match, user is unauthorized
    throw new DataAccessException(401, "unauthorized");
  }
  public void clear(){
    authDataArray.clear();
  }
}
