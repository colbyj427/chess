package dataAccess;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.gson.Gson;
import model.UserRecord;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.ResultSet;

public class MySQLUserDao implements UserDaoInterface{
  public UserRecord getUser(String username, String password) throws DataAccessException {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String hashedPassword = encoder.encode(password);
    try {
      // replace the select variables with the actual arguments, and i have to hash the password probably.
      var statement="SELECT username, password, email FROM users WHERE username = ? AND password = ?;";
      var conn=DatabaseManager.getConnection();
      try (var preparedStatement=conn.prepareStatement(statement)) {
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, hashedPassword);
        var rs=preparedStatement.executeQuery();
        if (!rs.next()) {
          throw new DataAccessException(401, "unauthorized");
        }
          return readUser(rs);
        }
      } catch (Exception e) {
        throw new DataAccessException(500, e.getMessage());
      }
    }

  public UserRecord addUser(String username, String password, String email) throws DataAccessException {

    // hash the password before adding it.

    UserRecord alreadyTaken = getUser(username, password);
    UserRecord newUser = new UserRecord(username, password, email);
    if (alreadyTaken.equals(newUser)) {
      throw new DataAccessException(403, "already taken");
    }
    var statement="INSERT INTO users (username, password, email) VALUES ( ?, ?, ?)";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.setString(1, username);
      preparedStatement.setString(2, password);
      preparedStatement.setString(3, email);
      preparedStatement.executeUpdate();
      return newUser;
    } catch (Exception dataAccessException) {
      throw new DataAccessException(500, dataAccessException.getMessage());
    }
  }

  public void clear() throws DataAccessException {
    var statement="DELETE FROM users;";
    try {
      var conn=DatabaseManager.getConnection();
      var preparedStatement=conn.prepareStatement(statement);
      preparedStatement.executeUpdate();
    } catch (Exception dataAccessException) {
      throw new DataAccessException(500, dataAccessException.getMessage());
    }
  }

    private UserRecord readUser(ResultSet rs) throws Exception {

    // has to unhash the password?

      String newUsername=rs.getString("username");
      String newPassword=rs.getString("password");
      String newEmail=rs.getString("email");
      return new UserRecord(newUsername, newPassword, newEmail);
    }
}
