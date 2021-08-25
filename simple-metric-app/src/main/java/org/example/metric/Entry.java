package org.example.metric;

import io.grpc.ManagedChannelBuilder;
import io.opentelemetry.api.metrics.GlobalMeterProvider;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.otlp.metrics.OtlpGrpcMetricExporter;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.SdkMeterProviderBuilder;
import io.opentelemetry.sdk.metrics.aggregator.AggregatorFactory;
import io.opentelemetry.sdk.metrics.common.InstrumentType;
import io.opentelemetry.sdk.metrics.data.AggregationTemporality;
import io.opentelemetry.sdk.metrics.export.IntervalMetricReader;
import io.opentelemetry.sdk.metrics.export.MetricExporter;
import io.opentelemetry.sdk.metrics.view.InstrumentSelector;
import io.opentelemetry.sdk.metrics.view.View;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Entry {

  public static void main(String[] args) {
    System.out.println("welcome to sample metrics example");

    // read host and port
    String host = "localhost";
    host = System.getenv("OTEL_COLLECTOR_HOST");

    int port = 4317;
    port = Integer.parseInt(System.getenv("OTEL_COLLECTOR_PORT"));

    // create a selector to select which instruments to customize:
    InstrumentSelector instrumentSelector = InstrumentSelector.builder()
        .setInstrumentType(InstrumentType.VALUE_RECORDER)
        .build();

    // prepare buckets
    List<Double> buckets = Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0,
        60.0, 70.0, 80.0, 90.0, 100.0);
    // create a specification of how you want the metrics aggregated:
    AggregatorFactory aggregatorFactory = AggregatorFactory.histogram(buckets,
        AggregationTemporality.CUMULATIVE
    );

    // register the view with the SdkMeterProviderBuilder
    SdkMeterProviderBuilder sdkMeterProviderBuilder = SdkMeterProvider.builder();

    sdkMeterProviderBuilder.registerView(instrumentSelector, View.builder()
        .setAggregatorFactory(aggregatorFactory).build());

    // create a global metric provider (holds all metrics)
    SdkMeterProvider sdkMeterProvider = sdkMeterProviderBuilder.buildAndRegisterGlobal();

    // export all the metrics in otlp format to an instance (localhost, 4317)
    MetricExporter metricExporter = OtlpGrpcMetricExporter.builder()
        .setChannel(ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext().build())
        .build();

    // Regularly export metrics every 10seconds
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

    // create different metrics
    CounterExample counterExample = new CounterExample(meter);
    HistogramExample histogramExample = new HistogramExample(meter);
    UpDownCounterExample upDownCounterExample = new UpDownCounterExample(meter);

    // run while loop, and sleep for sometime.
    System.out.println("starting while loop");
    int counter = 0;
    while(true) {
      waitForSec();

      System.out.println("Updating metrics randomly after a sec");
      // calculate disk space every 1 seconds
      counterExample.makeRequest();

      // do latency work every 1 seconds
      histogramExample.doWork();

      // do some work and increment memory usage
      upDownCounterExample.updateUsage();

      // report metrics every 10 seconds explicitly, and reset counter
      counter++;
      if (counter % 10 == 0) {
        System.out.println("Reporting metrics after a 10 secs, and resetting counter");
        metricExporter.export(sdkMeterProvider.collectAllMetrics());
        counter = 0;
      }
    }
  }

  static void waitForSec() {
    System.out.println("waiting for a sec");
    try {
      Thread.sleep(1000L);
    } catch (InterruptedException e) {
      System.out.println("Interuppted");
    }
  }
}
