package server;

import dataAccess.DataAccessException;
import model.AuthRecord;
import model.UserRecord;
import service.UserService;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

public class UserHandler {
  UserService userService = new UserService();
  public String register(Request req, Response res) throws DataAccessException {
    try {
      var serializer=new Gson();
      UserRecord userInfo = serializer.fromJson(req.body(), UserRecord.class);
      AuthRecord authRecordReturn = userService.register(userInfo.username(), userInfo.password(), userInfo.email());
      res.status(200);
      String jsonAuthRecord = serializer.toJson(authRecordReturn);
      res.body(jsonAuthRecord);
      return serializer.toJson(res);
    }
    catch(DataAccessException dataAccessException) {
      res.status(400);
      res.body("message: ERROR: bad request");
      var serializer = new Gson();
      return serializer.toJson(res);
    }
  }
}
