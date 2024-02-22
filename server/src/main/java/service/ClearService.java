package service;

import dataAccess.UserDaoInterface;

import server.Server;


public class ClearService {

  public void clear(){
    Server.memoryUserDao.clear();
    Server.memoryAuthDao.clear();
    Server.memoryGameDao.clear();
  }
}
