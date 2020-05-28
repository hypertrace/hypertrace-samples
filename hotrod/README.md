# Hot R.O.D. - Rides on Demand

This is a demo application that consists of several microservices and illustrates
the use of the OpenTracing API. It can be run standalone, but requires Jaeger backend
to view the traces. A tutorial / walkthough is available:
  * as a blog post [Take OpenTracing for a HotROD ride][hotrod-tutorial],
  * as a video [OpenShift Commons Briefing: Distributed Tracing with Jaeger & Prometheus on Kubernetes][hotrod-openshift].

## Running

### Run everything via `docker-compose`

* Run HotROD demo with `docker-compose up`
* Access Hypertrace UI at http://localhost and HotROD app at http://localhost:8080
* Shutdown / cleanup with `docker-compose down`

Alternatively, you can run each component separately as described below.


### Run HotROD from docker
```bash
docker run \
  --rm \
  --link jaeger \
  --env JAEGER_ENDPOINT=http://docker.for.mac.localhost:14268/api/traces \
  -p8080-8083:8080-8083 \
  jaegertracing/example-hotrod:latest \
  all
```

Then open http://127.0.0.1:8080


[hotrod-tutorial]: https://medium.com/@YuriShkuro/take-opentracing-for-a-hotrod-ride-f6e3141f7941
[hotrod-openshift]: https://blog.openshift.com/openshift-commons-briefing-82-distributed-tracing-with-jaeger-prometheus-on-kubernetes/
