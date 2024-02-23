package server;

import dataAccess.DataAccessException;
import dataAccess.ErrorResponse;
import model.AuthRecord;
import model.UserRecord;
import service.UserService;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

public class UserHandler {
  private static final UserService userService = new UserService();

  public static String register(Request req, Response res) throws DataAccessException {
    try {
      var serializer=new Gson();
      UserRecord userInfo = serializer.fromJson(req.body(), UserRecord.class);
      return serializer.toJson(userService.register(userInfo.username(), userInfo.password(), userInfo.email()));
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

  public static String login(Request req, Response res) throws DataAccessException {
    try {
      var serializer=new Gson();
      UserRecord userInfo = serializer.fromJson(req.body(), UserRecord.class);
      return serializer.toJson(userService.login(userInfo.username(), userInfo.password()));
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
  public static String logout(Request req, Response res) throws DataAccessException {
    try {
      var serializer=new Gson();
      AuthRecord authInfo = serializer.fromJson(req.body(), AuthRecord.class);
      userService.logout(authInfo.authToken());
      res.status(200);
      return serializer.toJson(res);
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
