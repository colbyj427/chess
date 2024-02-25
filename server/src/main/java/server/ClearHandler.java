package server;

import dataAccess.DataAccessException;
import dataAccess.ErrorResponse;
import service.ClearService;
import spark.*;
import com.google.gson.Gson;

public class ClearHandler {
  private static ClearService clearService=new ClearService();

  public static String clear(Request req, Response res) throws DataAccessException {
    try {
      clearService.clear();
      res.status(200);
      var serializer=new Gson();
      return "{}";
    } catch (DataAccessException dataAccessException){
      ErrorResponse caughtError = new ErrorResponse(dataAccessException.statusCode, dataAccessException.message);
      res.status(dataAccessException.statusCode);
      res.body(dataAccessException.message);
      var serializer = new Gson();
      return serializer.toJson(caughtError);
    }
  }
}
