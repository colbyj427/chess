package dataAccess;

import model.AuthRecord;
import org.eclipse.jetty.client.api.Authentication;

import java.util.Collection;
import java.util.UUID;

public class MemoryAuthDao implements AuthDaoInterface{
  private Collection<AuthRecord> authDataArray;

  public AuthRecord addAuth(String username) throws DataAccessException{
    String authToken = UUID.randomUUID().toString();
    AuthRecord auth = new AuthRecord(authToken, username);
    authDataArray.add(auth);
    return auth;
  }
  public void clear(){
    authDataArray.clear();
  }
}
