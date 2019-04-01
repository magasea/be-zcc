package com.wensheng.zcc.common.mq.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import org.apache.kafka.common.serialization.Serializer;


/**
 * @author chenwei on 4/1/19
 * @project miniapp-backend
 */
public class GsonSerializer<T> implements Serializer<T> {

  private Gson gson = new GsonBuilder().create();

  @Override
  public void configure(Map<String, ?> config, boolean isKey) {
    // this is called right after construction
    // use it for initialisation
  }

  @Override
  public byte[] serialize(String s, T t) {
    return gson.toJson(t).getBytes();
  }

  @Override
  public void close() {
    // this is called right before destruction
  }
}
