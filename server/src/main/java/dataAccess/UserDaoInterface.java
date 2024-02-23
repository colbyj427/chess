package dataAccess;
import model.UserRecord;
public interface UserDaoInterface {
    UserRecord getUser(String username, String password, String email) throws DataAccessException;
    UserRecord addUser(String username, String password, String email) throws DataAccessException;

    void clear();
}
