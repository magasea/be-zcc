package com.wensheng.zcc.common.mq.kafka;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chenwei on 4/2/19
 * @project miniapp-backend
 */
@Slf4j
public class DateTimeDeserializer implements JsonDeserializer {

  String pattern = "MM/dd/yyyy HH:mm:ss";
  DateFormat df = new SimpleDateFormat(pattern);

  @Override
  public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    try {
      String result = json.getAsJsonPrimitive().getAsString();
      log.info(result);
      return df.parse(result);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

}
