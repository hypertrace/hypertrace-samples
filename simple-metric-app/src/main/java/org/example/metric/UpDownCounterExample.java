package org.example.metric;

import io.opentelemetry.api.metrics.BoundDoubleUpDownCounter;
import io.opentelemetry.api.metrics.DoubleCounter;
import io.opentelemetry.api.metrics.DoubleUpDownCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;

public class UpDownCounterExample {
  private DoubleUpDownCounter doubleUpDownCounter;
  private BoundDoubleUpDownCounter someWorkBound;

  public UpDownCounterExample(Meter meter) {

    this.doubleUpDownCounter = meter
        .doubleUpDownCounterBuilder("gauge_example_memory_useage")
        .setDescription("Randomly measure the memory usage")
        .setUnit("MB")
        .build();

    someWorkBound = doubleUpDownCounter.bind(Labels.of("memory_gauge_usage_key", "memory_gauge_usage_value"));
  }

  public void updateUsage() {
    Double cpuUsage = Math.random() * 1000;
    if (Math.random() > 0.5) {
      someWorkBound.add(-cpuUsage);
    } else {
      someWorkBound.add(cpuUsage);
    }
  }
}
