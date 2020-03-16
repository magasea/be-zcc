package com.wensheng.zcc.common.mq.kafka;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwei on 4/2/19
 * @project miniapp-backend
 */
@Slf4j
public class LocalDateTimeDeserializer implements JsonDeserializer {

  @Override
  public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String result = json.getAsJsonPrimitive().getAsString();
    log.info(result);
    return LocalDateTime.parse(result);
  }

}
