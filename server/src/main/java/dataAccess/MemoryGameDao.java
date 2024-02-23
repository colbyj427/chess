package dataAccess;

import model.GameRecord;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryGameDao implements GameDaoInterface{
  private Collection<GameRecord> gameDataArray = new ArrayList<>();

  public void clear() {
    gameDataArray.clear();
  }
}
