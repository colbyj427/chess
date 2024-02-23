package service;

import dataAccess.DataAccessException;

import server.Server;


public class ClearService {

  public void clear() throws DataAccessException {
    Server.memoryUserDao.clear();
    Server.memoryAuthDao.clear();
    Server.memoryGameDao.clear();
  }
}
