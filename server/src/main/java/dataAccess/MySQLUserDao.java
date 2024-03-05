package dataAccess;

import java.sql.DriverManager;
import java.sql.SQLException;
import model.UserRecord;


public class MySQLUserDao {
  public UserRecord getUser(String username, String password) throws DataAccessException{
    try {
      var statement = "SELECT username AS username, password AS password, email AS email, FROM users WHERE users.username = username AND users.password = password;";
      var conn = DatabaseManager.getConnection();
      try (var preparedStatement = conn.prepareStatement(statement)) {
        var rs = preparedStatement.executeQuery();
        String newUsername = rs.getString("username");
        String newPassword = rs.getString("password");
        String newEmail = rs.getString("email");
        return new UserRecord(newUsername, newPassword, newEmail);
      }
    } catch (SQLException e) {
      throw new DataAccessException(500, e.getMessage());
    }
  }
  public UserRecord addUser(String username, String password, String email) throws DataAccessException{
    var statement = "INSERT INTO users (username, password, email) VALUES ( ?, ?, ?)";
//    var preparedStatement =
    statement.executeUpdate(statement, username, password, email);
  }
  public void clear(){
    // How do i delete all the rows?
    var statement = "DELETE FROM users WHERE *;"
  }
}
