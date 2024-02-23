package dataAccess;

import model.AuthRecord;

public interface AuthDaoInterface {
  AuthRecord addAuth(String username) throws DataAccessException;
  void removeAuth(String authToken) throws DataAccessException;
  void clear();
}
