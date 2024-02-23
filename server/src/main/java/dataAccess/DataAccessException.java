package dataAccess;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends Exception{
    public String message;
    public int statusCode;
    public DataAccessException(int statusCode, String message) {
        this.message = "Error: " + message;
        this.statusCode = statusCode;
    }
}
