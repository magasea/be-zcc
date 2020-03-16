package com.wensheng.zcc.common.mq.kafka;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwei on 4/2/19
 * @project miniapp-backend
 */
@Slf4j
public class DateTimeSerializer implements JsonSerializer {

  String pattern = "MM/dd/yyyy HH:mm:ss";
  DateFormat df = new SimpleDateFormat(pattern);
  @Override
  public JsonElement serialize(Object o, Type type, JsonSerializationContext jsonSerializationContext) {
    if((o instanceof LocalDateTime || type == LocalDateTime.class) && o != null){
      String result = ((LocalDateTime) o).toString();
      log.info(result);
      return new JsonPrimitive(result);
    }
    if((o instanceof Date) && o != null){
      String result = df.format((Date) o);
      return new JsonPrimitive(result);
    }
    return null;

//    return new JsonPrimitive(o.toString());
  }
}
