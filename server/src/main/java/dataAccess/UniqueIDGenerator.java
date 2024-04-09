package dataAccess;

import java.util.concurrent.atomic.AtomicInteger;

public class UniqueIDGenerator {
  public static final AtomicInteger idGenerator = new AtomicInteger(0);

  public static int generateUniqueID() {
    return idGenerator.incrementAndGet();
  }
}