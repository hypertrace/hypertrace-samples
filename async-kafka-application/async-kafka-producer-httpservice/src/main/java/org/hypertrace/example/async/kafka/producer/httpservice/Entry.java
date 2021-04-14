package org.hypertrace.example.async.kafka.producer.httpservice;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Entry {

  private static final Logger LOGGER = LoggerFactory.getLogger(Entry.class);
  private static HttpServer server = new HttpServer();

  public static void main(String[] args) {
    try {
      Config config = ConfigFactory.parseResources("application.conf").resolve();
      updateRuntime();
      server.init(config);
      server.start();
    } catch (Exception e) {
      LOGGER.error("Failed to start due to error", e);
    }
  }

  private static void updateRuntime() {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      try {
        server.stop();
      } catch (Throwable e) {
        LOGGER.error("Failed with error", e);
      }
    }));
  }
}
