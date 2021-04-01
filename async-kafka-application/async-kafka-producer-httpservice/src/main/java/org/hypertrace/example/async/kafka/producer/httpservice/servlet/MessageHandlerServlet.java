package org.hypertrace.example.async.kafka.producer.httpservice.servlet;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomUtils;
import org.hypertrace.example.async.kafka.producer.httpservice.producer.RecordProducer;

public class MessageHandlerServlet extends HttpServlet {

  private static final String PLAIN_TEXT_UTF_8 = "text/plain; charset=utf-8";
  private RecordProducer producer;
  int maxProcessingDelay;

  public MessageHandlerServlet(RecordProducer producer, int maxProcessingDelay) {
    this.producer = producer;
    this.maxProcessingDelay = maxProcessingDelay;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    try {
      String respStr = "OK";
      Thread.sleep(RandomUtils.nextInt(1, 10));
      producer.sendMessage("Hello:");
      resp.setStatus(200);
      resp.setContentType(PLAIN_TEXT_UTF_8);
      resp.getOutputStream().print(respStr);
    } catch (InterruptedException e) {
      throw new IOException("failed to send record");
    }
  }

}
