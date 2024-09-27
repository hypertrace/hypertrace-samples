package org.example.metric;

import io.opentelemetry.api.metrics.BoundLongValueRecorder;
import io.opentelemetry.api.metrics.LongValueRecorder;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;

public class HistogramExample {
  private LongValueRecorder valueRecorder;
  private int maxLatency = 100;
  private int instances = 2;

  public HistogramExample(Meter meter) {
    valueRecorder =
        meter.longValueRecorderBuilder("latency")
        .setDescription("Randomly measure the time taken by an api")
        .setUnit("ms")
        .build();
  }

  public void doWork() {
    for(int i = 0; i < instances; i++) {
      Labels labels = Labels.of(
          "instance_id", String.format("simple-metric-app-instance-%d", i),
          "service_name", "simple-metrics-app",
          "api_name","/api/v1/request");

      long timeTaken = (long) (Math.random() * maxLatency);
      valueRecorder.record(timeTaken, labels);
    }
  }
}
