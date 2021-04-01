package org.hypertrace.example.async.kafka.consumer;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Entry {

  private static final Logger LOGGER = LoggerFactory.getLogger(Entry.class);

  public static void main(String[] args) {
    try {
      Config config = ConfigFactory.parseResources("application.conf");
      String bootstrapServer = config.getString("kafka.bootstrap.server");
      String consumerTopic = config.getString("kafka.consumer.topic");
      int maxWaitTime = config.getInt("maxWaitTime");
      RecordConsumer consumer = new RecordConsumer(bootstrapServer, consumerTopic);
      while (true) {
        try {
          Thread.sleep(RandomUtils.nextInt(1, maxWaitTime));
          consumer.read();
        } catch (InterruptedException e) {
          LOGGER.error("Wait was Interrupted due to", e);
        }
      }
    } catch (Exception e) {
      LOGGER.error("Failed due to error", e);
    }
  }
}
