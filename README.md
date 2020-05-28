# HyperTrace-samples

There are 5 sample apps in this repo which are compatible with hypertrace and sending traces to platform. Please visit `README.md` in each app folder to get more information about application and changes made in them. I have listed steps to execute each of them in short here. For detailed instructions visit individual docs.

## Online Boutique:

1. Clone this repository, and go to the repository directory
2. Run `kubectl apply -f ./release/kubernetes-manifests.yaml` to deploy the app.
3. Run `kubectl get pods` to see pods are in a Ready state.
4. Find the `NodePort` of your application, then visit the application at `localhost:nodeport` on your
   browser to confirm installation. 

   ```sh
   kubectl get service/frontend-external
   ```

## Hotrod demo:
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

## MicroDonuts Demo:
1. Run using any IDE or Terminal.
2. visit `localhost:10001` and order your donuts.
3. check if you are able to see traces on hypertrace platform by the times donuts are ready!
4. Check the documentation [here](https://github.com/opentracing-contrib/java-opentracing-walkthrough/blob/master/README.md) if you want to add some other tracer or modify something. 

## Spring Cloud Sleuth:
1. start all 4 services and visit `http://localhost:8081/zipkin`.
2. Visit the hypertrace UI and you can see traces there!
3. Service issue is there so you will all service names as `zipkin-server1` only. 
