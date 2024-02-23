package server;

import dataAccess.DataAccessException;
import service.ClearService;
import spark.*;
import com.google.gson.Gson;

public class ClearHandler {
  private ClearService clearService=new ClearService();

  public String clear(Request req, Response res) throws DataAccessException {
    clearService.clear();
    res.status(200);
    return new Gson().toJson(res);
  }
}
