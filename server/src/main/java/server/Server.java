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
        Spark.delete("/db", this::deleteAll);

        Spark.init();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object deleteAll(Request req, Response res) throws DataAccessException {
        try {
            ClearHandler clearHandler=new ClearHandler();
            return clearHandler.clear(req, res);
        }
        catch (DataAccessException dataAccessException){
            throw new DataAccessException("500");
        }
    }

    public static void main(String[] args) {
        new Server().run(8080);
    }
}
