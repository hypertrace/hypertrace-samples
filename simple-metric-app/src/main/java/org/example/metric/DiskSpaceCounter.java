package org.example.metric;

import io.opentelemetry.api.metrics.DoubleCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DiskSpaceCounter {
  private DoubleCounter diskSpaceCounter;
  private List<String> extensionsToFind;
  private File directory;

  public DiskSpaceCounter(Meter meter, File directory) {
    this.extensionsToFind = new ArrayList<String>() {
      { add("png"); }
    };

    this.directory = directory;

    this.diskSpaceCounter = meter
            .doubleCounterBuilder("calculated_used_space")
            .setDescription("Counts disk space used by file extension.")
            .setUnit("MB")
            .build();
  }

  public void calculate() {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        for (String extension : extensionsToFind) {
          if (file.getName().endsWith("." + extension)) {
            // we can add values to the counter for specific labels
            // the label key is "file_extension", its value is the name of the extension
            diskSpaceCounter.add(
                (double) file.length() / 1_000_000, Labels.of("file_extension", extension));
          }
        }
      }
    }
  }

}
