package org.example.metric;

import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.api.metrics.DoubleCounter;
import io.opentelemetry.api.metrics.GlobalMeterProvider;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.common.Labels;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.IntervalMetricReader;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Entry {

  public static void main(String[] args) {
    // create a global metric provider (holds all metrics)
    SdkMeterProvider sdkMeterProvider = SdkMeterProvider.builder().buildAndRegisterGlobal();

    // export all the metrics in otlp format to an instance (localhost, 4317)
    MetricExporter metricExporter = OtlpGrpcMetricExporter.builder()
        .setChannel(ManagedChannelBuilder.forAddress("localhost", 4317)
            .usePlaintext().build())
        .build();

    // Regularly export metrics.
    // is this part working? I doubt that.
    IntervalMetricReader intervalMetricReader =
        IntervalMetricReader.builder()
            .setMetricProducers(
                Collections.singleton(sdkMeterProvider))
            .setExportIntervalMillis(10000)// configurable interval
            .setMetricExporter(metricExporter)
            .build();

    // get global meter instance
    Meter meter =
        GlobalMeterProvider.get().get("io.opentelemetry.example.metrics", "1.4.1");

    DoubleCounter diskSpaceCounter =
        meter
            .doubleCounterBuilder("calculated_used_space")
            .setDescription("Counts disk space used by file extension.")
            .setUnit("MB")
            .build();

    Entry example = new Entry();
    List<String> extensionsToFind = new ArrayList<>();
    extensionsToFind.add("png");
    for (int i = 0; i < 100; i++) {
      example.calculateSpaceUsedByFilesWithExtension(extensionsToFind,
          new File("/Users/ronak/Desktop"), diskSpaceCounter);
      try {
        Thread.sleep((long)(Math.random() * 1000));
      } catch (InterruptedException e) {
        System.out.println("Intruppted");
      }

      if (i % 10 == 0) {
        metricExporter.export(sdkMeterProvider.collectAllMetrics());
      }

    }


  }


  public void calculateSpaceUsedByFilesWithExtension(List<String> extensions, File directory, DoubleCounter diskSpaceCounter) {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        for (String extension : extensions) {
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
