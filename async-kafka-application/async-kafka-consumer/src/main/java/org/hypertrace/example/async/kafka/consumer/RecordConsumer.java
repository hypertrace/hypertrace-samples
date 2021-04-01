package org.hypertrace.example.async.kafka.consumer;

import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RecordConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Entry.class);
  private Consumer consumer;

  public RecordConsumer(String bootstrapServer, String topicName) {
    Properties props = new Properties();
    props.put("bootstrap.servers", bootstrapServer);
    props.put("group.id", "record-consumer");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Arrays.asList(topicName));
  }

  public void read() {
    ConsumerRecords<String, String> records = consumer.poll(100);
    for (ConsumerRecord<String, String> record : records) {
      LOGGER.info(String.format("offset = %d, key = %s, value = %s%n",
          record.offset(), record.key(), record.value()));
    }
  }
}
