package org.hypertrace.example.async.kafka.producer.httpservice.producer;

import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;


public class RecordProducer {

  private Producer producer;
  private String topicName;
  private int lastRecord;

  public RecordProducer(String bootstrapSever, String topicName) {

    Properties props = new Properties();
    props.put("bootstrap.servers", bootstrapSever);
    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 16384);
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    this.producer = new KafkaProducer<>(props);
    this.topicName = topicName;
    this.lastRecord = 0;
  }

  public void sendMessage(String message) {
    producer.send(new ProducerRecord<String, String>(topicName,
        Integer.toString(lastRecord),
        String.format("%s:%s", message, lastRecord)));
    lastRecord++;
  }

  public void close() {
    producer.close();
  }

}
