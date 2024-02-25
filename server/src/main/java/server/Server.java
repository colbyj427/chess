package server;

import dataAccess.*;
import spark.*;

public class Server {
    public static UserDaoInterface memoryUserDao = new MemoryUserDao();
    public static AuthDaoInterface memoryAuthDao = new MemoryAuthDao();
    public static GameDaoInterface memoryGameDao = new MemoryGameDao();


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", UserHandler::register);
        Spark.post("/session", UserHandler::login);
        Spark.delete("/session", UserHandler::logout);
        Spark.get("/game", GameHandler::listGames);
        Spark.post("/game", GameHandler::createGame);
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
