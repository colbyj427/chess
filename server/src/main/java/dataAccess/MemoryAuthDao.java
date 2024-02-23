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
  public void removeAuth(String authToken) throws DataAccessException{
    for (AuthRecord record: authDataArray){
      if (record.authToken() == authToken) {
        authDataArray.remove(record);
      }
    }
    //if authToken doesnt match, user is unauthorized
    throw new DataAccessException(401, "unauthorized");
  }
  public void clear(){
    authDataArray.clear();
  }
}
