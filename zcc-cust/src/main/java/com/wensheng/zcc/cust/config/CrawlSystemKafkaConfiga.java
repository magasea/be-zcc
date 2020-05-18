package com.wensheng.zcc.cust.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


/**
 * @author wsz on 13/5/20
 * @project miniapp-backend
 */
@Configuration
public class CrawlSystemKafkaConfiga {

  @Value("${spring.kafka.crawl-system.bootstrap-servers}")
  private String bootstrapServers;
  @Value("${spring.kafka.crawl-system.consumer.group-id}")
  private String groupId;
  @Value("${spring.kafka.crawl-system.consumer.enable-auto-commit}")
  private boolean enableAutoCommit;


  @Bean
  public Map<String, Object> crawlSystemProducerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.RETRIES_CONFIG, 0);
    props.put(ProducerConfig.ACKS_CONFIG, "1");
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return props;
  }

  @Bean
  public Map<String, Object> crawlSystemConsumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return props;
  }

  @Bean
  public ProducerFactory<String, Object> crawlSystemProducerFactory() {
    return new DefaultKafkaProducerFactory<>(crawlSystemProducerConfigs());
  }

  @Bean
  public ConsumerFactory<String, Object> crawlSystemConsumerFactory() {
//    final GsonDeserializer<Object> gsonSerializer = new GsonDeserializer<>();
//    Map<String, String> config = new HashMap<>();
//    config.put(GsonDeserializer.CONFIG_VALUE_CLASS, WechatUserLocation.class.getName());
//    gsonSerializer.configure(config, false);
//    gsonSerializer.close();
    return new DefaultKafkaConsumerFactory<>(crawlSystemConsumerConfigs());
  }


  @Bean
  public KafkaTemplate<String, Object> crawlSystemKafkaTemplate() {
    return new KafkaTemplate<>(crawlSystemProducerFactory());
  }


  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, Object> crawlSystemKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(crawlSystemConsumerFactory());
    factory.setConcurrency(3);
    factory.getContainerProperties().setPollTimeout(3000);
    return factory;
  }


}
