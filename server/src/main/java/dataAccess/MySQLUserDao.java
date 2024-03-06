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
    try {
      var statement="SELECT username, password, email FROM users WHERE username = ?;";
      var conn=DatabaseManager.getConnection();
      try (var preparedStatement=conn.prepareStatement(statement)) {
        preparedStatement.setString(1, username);
        var rs=preparedStatement.executeQuery();
        if (!rs.next()) {
          throw new DataAccessException(401, "unauthorized");
        }
        String hashedPassword = rs.getString("password");
        if (!encoder.matches(password, hashedPassword)) {
          throw new DataAccessException(401, "unauthorized");
        }
        return readUser(rs);
        }
      } catch (Exception e) {
        throw new DataAccessException(401, e.getMessage());
      }
    }

  public UserRecord addUser(String username, String password, String email) throws DataAccessException {
    try {
      //if get user returns an error because it doesnt exist, we should add it.
      getUser(username, password);
      //putting that throw in a catch fixes the double registration test but fails all the rest.
      throw new SQLException();
    } catch (DataAccessException e){
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      String hashedPassword = encoder.encode(password);
      UserRecord newUser=new UserRecord(username, password, email);
      var statement="INSERT INTO users (username, password, email) VALUES ( ?, ?, ?)";
      try {
        var conn=DatabaseManager.getConnection();
        var preparedStatement=conn.prepareStatement(statement);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, hashedPassword);
        preparedStatement.setString(3, email);
        preparedStatement.executeUpdate();
        return newUser;
      } catch (Exception dataAccessException) {
        throw new DataAccessException(500, dataAccessException.getMessage());
      }
    }
    catch (SQLException e) {
      throw new DataAccessException(403, "already taken");
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
