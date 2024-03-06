package server;

import dataAccess.*;
import spark.*;

import java.sql.SQLException;

public class Server {
    public static UserDaoInterface memoryUserDao = new MySQLUserDao();
    public static AuthDaoInterface memoryAuthDao = new MySQLAuthDao();
    public static GameDaoInterface memoryGameDao = new MySQLGameDao();


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        try {
            DatabaseManager.configureDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", UserHandler::register);
        Spark.post("/session", UserHandler::login);
        Spark.delete("/session", UserHandler::logout);
        Spark.get("/game", GameHandler::listGames);
        Spark.post("/game", GameHandler::createGame);
        Spark.put("/game", GameHandler::joinGame);
        Spark.delete("/db", ClearHandler::clear);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    public static void main(String[] args) {
        new Server().run(8080);
    }
}
