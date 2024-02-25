package dataAccess;

import model.AuthRecord;

public interface AuthDaoInterface {
  AuthRecord getAuth(String authToken) throws DataAccessException;
  AuthRecord addAuth(String username) throws DataAccessException;
  void removeAuth(String authToken) throws DataAccessException;
  void clear();
}
