package org.example.metric;

import io.opentelemetry.api.metrics.BoundLongValueRecorder;
import io.opentelemetry.api.metrics.LongValueRecorder;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;

public class SummaryExample {
  private LongValueRecorder valueRecorder;
  BoundLongValueRecorder someWorkBound;

  public SummaryExample(Meter meter) {
    valueRecorder =
        meter.longValueRecorderBuilder("summary_example_latency")
        .setDescription("method execution time as Latency")
        .setUnit("ms")
        .build();

    someWorkBound =
        valueRecorder.bind(Labels.of("summary_work", "method_doWork"));
  }

  public void doWork() {
    long startTime = System.nanoTime();
    waitForRandom();
    someWorkBound.record(System.nanoTime() - startTime);
  }

  private void waitForRandom() {
    try {
      Thread.sleep((long)(Math.random() * 1000));
    } catch (InterruptedException e) {
      System.out.println("Interuppted");
    }
  }
}
