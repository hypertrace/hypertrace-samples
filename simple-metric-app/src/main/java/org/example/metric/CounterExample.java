package org.example.metric;

import io.opentelemetry.api.metrics.DoubleCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;

public class CounterExample {
  private DoubleCounter doubleCounter;

  public CounterExample(Meter meter) {

    this.doubleCounter = meter
        .doubleCounterBuilder("counter_example_space_used")
        .setDescription("Randomly increment the counter as disk space")
        .setUnit("MB")
        .build();
  }

  public void calculate() {

    // we can add values to the counter for specific labels
    // the label key is "file_extension", its value is the name of the extension

    Double fileLength = Math.random() * 100;
    doubleCounter.add(fileLength, Labels.of("counter_example", "random_disk_space"));
  }
}
