package dataAccess;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerAdapter implements JsonSerializer<AtomicInteger>, JsonDeserializer<AtomicInteger> {

  @Override
  public JsonElement serialize(AtomicInteger src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.get());
  }

  @Override
  public AtomicInteger deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    return new AtomicInteger(json.getAsInt());
  }
}

