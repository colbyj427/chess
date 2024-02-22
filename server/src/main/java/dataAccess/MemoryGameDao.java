package dataAccess;

import model.GameRecord;

import java.util.Collection;

public class MemoryGameDao implements GameDaoInterface{
  private Collection<GameRecord> gameDataArray;

  public void clear() {
    gameDataArray.clear();
  }
}
