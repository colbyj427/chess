package dataAccess;

import model.AuthRecord;
import model.UserRecord;

import java.sql.ResultSet;
import java.util.UUID;

public class MySQLAuthDao implements AuthDaoInterface {
  public AuthRecord getAuth(String authToken) throws DataAccessException{
    var statement="SELECT authToken, username FROM authentication WHERE authToken = ?;";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.setString(1, authToken);
      ResultSet rs = preparedStatement.executeQuery();
      rs.next();
      return readAuth(rs);
    } catch (Exception dataAccessException) {
      throw new DataAccessException(401, "unauthorized");
    }
  }
  public AuthRecord addAuth(String username) throws DataAccessException{
    if (username == null | username.length() == 0) {
      throw new DataAccessException(400, "bad request");
    }
    String authToken = UUID.randomUUID().toString();
    AuthRecord auth = new AuthRecord(authToken, username);
    var statement="INSERT INTO authentication (username, authToken) VALUES ( ?, ?)";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, authToken);
      preparedStatement.executeUpdate();
      return auth;
    } catch (Exception dataAccessException) {
      throw new DataAccessException(400, "bad request");
    }
  }
  public void removeAuth(String authToken) throws DataAccessException{
    var statement="DELETE FROM authentication WHERE authToken = ?;";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.setString(1, authToken);
      int numDeleted = preparedStatement.executeUpdate();
      if (numDeleted == 0) {
        throw new DataAccessException(401, "unauthorized");
      }
    } catch (Exception dataAccessException) {
      throw new DataAccessException(401, "unauthorized");
    }
  }
  public void clear() throws DataAccessException {
    var statement="DELETE FROM authentication;";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.executeUpdate();
    } catch (Exception dataAccessException) {
      throw new DataAccessException(500, dataAccessException.getMessage());
    }
  }
  private AuthRecord readAuth(ResultSet rs) throws Exception {
    String newAuthToken=rs.getString("authToken");
    String newUsername=rs.getString("username");
    return new AuthRecord(newAuthToken, newUsername);
  }
}
