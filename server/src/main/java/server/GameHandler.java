package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dataAccess.AtomicIntegerAdapter;
import dataAccess.DataAccessException;
import dataAccess.ErrorResponse;
import model.GameRecord;
import model.JoinGameRecord;
import service.GameService;
import spark.Request;
import spark.Response;

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
      Gson gson = new GsonBuilder().registerTypeAdapter(AtomicInteger.class, new AtomicIntegerAdapter())
              .create();
      return serializer.toJson(gameRecord);
    }
    catch(DataAccessException createGameException) {
      //create a status response class. a record with a status code and message, make one out of the exception thrown and return that.
      ErrorResponse caughtError = new ErrorResponse(createGameException.statusCode, createGameException.message);
      res.status(createGameException.statusCode);
      res.body(createGameException.message);
      var serializer = new Gson();
      return serializer.toJson(caughtError);
    }
  }
  public static String listGames(Request req, Response res) throws DataAccessException{
    try {
      //var serializer = new Gson();
      String authToken = req.headers("Authorization");
      res.status(200);
      Collection<GameRecord> listOfGames = gameService.listGames(authToken);
      JsonObject jsonObject = new JsonObject();
      jsonObject.add("games", new Gson().toJsonTree(listOfGames));
      String jsonString = jsonObject.toString();
      return jsonString;

    }
    catch(DataAccessException listGamesException) {
      ErrorResponse caughtError = new ErrorResponse(listGamesException.statusCode, listGamesException.message);
      res.status(listGamesException.statusCode);
      res.body(listGamesException.message);
      var serializer = new Gson();
      return serializer.toJson(caughtError);
    }
  }
  public static String joinGame(Request req, Response res) throws DataAccessException{
    try {
      var serializer = new Gson();
      String authToken = req.headers("authorization");
      JoinGameRecord joinGameRecord= serializer.fromJson(req.body(), JoinGameRecord.class);
      gameService.joinGame(authToken, joinGameRecord.playerColor(), joinGameRecord.gameID());
      return "{}";
    }
    catch(DataAccessException joinGameException) {
      ErrorResponse caughtError = new ErrorResponse(joinGameException.statusCode, joinGameException.message);
      res.status(joinGameException.statusCode);
      res.body(joinGameException.message);
      var serializer = new Gson();
      return serializer.toJson(caughtError);
    }
  }
}
