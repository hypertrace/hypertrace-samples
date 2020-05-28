## About this demo:
For this demo, we have created 4 spring boot based microservices. They all will have both Zipkin and Sleuth starter dependencies. In each microservice, we will expose one endpoint and from the first service we will call second service, and from second service we will invoke third and so on using rest Template.

And as we have already mentioned, Sleuth works automatically with rest template so it would send this instrumented service call information to attached Zipkin server. Zipkin will then start the book keeping of latency calculation along with few other statistics like service call details.

![](https://howtodoinjava.com/wp-content/uploads/2017/08/5-3.jpg)

## Steps to run:
1. start all 4 services and visit `http://localhost:8081/zipkin`.
2. Visit the hypertrace UI and you can see traces there!
3. Service issue is there so you will all service names as `zipkin-server1` only. 

