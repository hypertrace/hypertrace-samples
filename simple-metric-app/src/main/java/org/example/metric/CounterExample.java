package org.example.metric;

import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;

public class CounterExample {
  private int maxRequest = 1000;
  private LongCounter longCounter;
  private int instances = 2;

  public CounterExample(Meter meter) {

    this.longCounter = meter
        .longCounterBuilder("request_count")
        .setDescription("Randomly increment the number of calls")
        .setUnit("NN")
        .build();
  }

  public void makeRequest() {
    for(int i = 0; i < instances; i++) {
      Labels labels = Labels.of(
          "instance_id", String.format("simple-metric-app-instance-%d", i),
          "service_name", "simple-metrics-app",
          "api_name","/api/v1/request");

      long numRequests = (long) (Math.random() * maxRequest);
      longCounter.add(numRequests, labels);
    }
  }
}
