package org.hypertrace.example.async.kafka.producer.httpservice;

import com.typesafe.config.Config;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.hypertrace.example.async.kafka.producer.httpservice.producer.RecordProducer;
import org.hypertrace.example.async.kafka.producer.httpservice.servlet.MessageHandlerServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

  private Server server;
  private RecordProducer producer;

  public void init(Config config) {
    int port = config.getInt("port");
    server = new Server(port);
    ServletContextHandler context = new ServletContextHandler();
    context.setContextPath("/");
    server.setHandler(context);
    server.setStopAtShutdown(true);
    server.setStopTimeout(2000);

    String bootstrapServer = config.getString("kafka.bootstrap.server");
    String producerTopic = config.getString("kafka.producer.topic");
    int maxProcessingDelay = config.getInt("maxProcessingDelay");
    producer = new RecordProducer(bootstrapServer, producerTopic);
    context.addServlet(new ServletHolder(
        new MessageHandlerServlet(producer, maxProcessingDelay)), "/send");
  }

  public void start() {
    try {
      server.start();
      LOGGER.info("HTTP server started");
      server.join();
    } catch (Exception e) {
      LOGGER.error("Failed to start service servlet.");
    }
  }

  public void stop() {
    try {
      producer.close();
      server.stop();
    } catch (Exception ex) {
      LOGGER.error("Error stopping admin server");
    }
  }
}
