package org.example.metric;

import io.opentelemetry.api.metrics.DoubleUpDownCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;

public class UpDownCounterExample {
  private DoubleUpDownCounter doubleUpDownCounter;
  private int instances = 2;
  private int upDown = 100;

  public UpDownCounterExample(Meter meter) {

    this.doubleUpDownCounter = meter
        .doubleUpDownCounterBuilder("memory_used")
        .setDescription("Randomly measure the memory usage")
        .setUnit("MB")
        .build();
  }

  public void updateUsage() {
    for(int i = 0;  i < instances; i++) {
      update(i);
    }
  }

  private void update(int instance) {
    Labels labels = Labels.of(
        "instance_id", String.format("simple-metrics-app-instance-%d", instance),
        "service_name", "simple-metrics-app");

    int changeInUsage = (int) (Math.random() * upDown);
    if (Math.random() > 0.65) /* going down */ {
      doubleUpDownCounter.add(-changeInUsage, labels);
    } else /* going up */ {
      doubleUpDownCounter.add(changeInUsage, labels);
    }
  }
}
