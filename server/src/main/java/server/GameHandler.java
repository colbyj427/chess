package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dataAccess.AtomicIntegerAdapter;
import dataAccess.DataAccessException;
import dataAccess.ErrorResponse;
import model.GameRecord;
import service.GameService;
import spark.Request;
import spark.Response;

import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class GameHandler {

  private static final GameService gameService = new GameService();
  public static String createGame(Request req, Response res) throws DataAccessException {
    try {
      var serializer=new Gson();
      String authToken = req.headers("Authorization");
      GameRecord gameInfo = serializer.fromJson(req.body(), GameRecord.class);
      GameRecord gameRecord = gameService.createGame(authToken, gameInfo.gameName());
      res.status(200);
      //res.body(gameID);
      Gson gson = new GsonBuilder().registerTypeAdapter(AtomicInteger.class, new AtomicIntegerAdapter())
              .create();
//      res.body(gson.toJson(gameID));
      return serializer.toJson(gameRecord);
    }
    catch(DataAccessException dataAccessException) {
      //create a status response class. a record with a status code and message, make one out of the exception thrown and return that.
      ErrorResponse caughtError = new ErrorResponse(dataAccessException.statusCode, dataAccessException.message);
      res.status(dataAccessException.statusCode);
      res.body(dataAccessException.message);
      var serializer = new Gson();
      return serializer.toJson(caughtError);
    }
  }
  public static String listGames(Request req, Response res) throws DataAccessException{
    try {
      var serializer = new Gson();
      String authToken = req.headers("Authorization");
      res.status(200);
      //      return serializer.toJson(gameService.listGames(authToken));
      Collection<GameRecord> listOfGames = gameService.listGames(authToken);
      JsonObject jsonObject = new JsonObject();
      jsonObject.add("gameList", new Gson().toJsonTree(listOfGames));
      String jsonString = jsonObject.toString();
      return jsonString;

    }
    catch(DataAccessException dataAccessException) {
      //create a status response class. a record with a status code and message, make one out of the exception thrown and return that.
      ErrorResponse caughtError = new ErrorResponse(dataAccessException.statusCode, dataAccessException.message);
      res.status(dataAccessException.statusCode);
      res.body(dataAccessException.message);
      var serializer = new Gson();
      return serializer.toJson(caughtError);
    }
  }
}
