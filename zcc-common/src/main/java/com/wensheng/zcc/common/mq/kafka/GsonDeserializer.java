package com.wensheng.zcc.common.mq.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Map;
import org.apache.kafka.common.serialization.Deserializer;

/**
 * @author chenwei on 4/1/19
 * @project miniapp-backend
 */
public class GsonDeserializer<T> implements Deserializer<T> {

  public static final String CONFIG_VALUE_CLASS = "value.deserializer.class";
  public static final String CONFIG_KEY_CLASS = "key.deserializer.class";
  private Class<T> cls;

  private Gson gson = new GsonBuilder().create();


  @Override
  public void configure(Map<String, ?> config, boolean isKey) {
    String configKey = isKey ? CONFIG_KEY_CLASS : CONFIG_VALUE_CLASS;
    String clsName = String.valueOf(config.get(configKey));

    try {
      cls = (Class<T>) Class.forName(clsName);
    } catch (ClassNotFoundException e) {
      System.err.printf("Failed to configure GsonDeserializer. " +
              "Did you forget to specify the '%s' property ?%n",
          configKey);
    }
  }


  @Override
  public T deserialize(String topic, byte[] bytes) {
    return (T) gson.fromJson(new String(bytes), cls);
  }


  @Override
  public void close() {}
}
