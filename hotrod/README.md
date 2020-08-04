## B. HotROD app:

### Industry: Consumer [Ride booking platform]
### Learning objectives:

- How to discover architecture of the whole system via data-driven dependency diagram?
- How to view request timeline & errors, understand how the app works?
- how to find sources of latency, lack of concurrency?
- How to achieve highly contextualized logging?
- How to use baggage propagation to
    - Diagnose inter-request contention (queueing)
    - Attribute time spent in a service
- How to use open source libraries with OpenTracing integration to get vendor-neutral instrumentation for free?


### Introduction
If you don't live in Antarctica or Amazon jungles you might have came across Ride booking and sharing platforms like Uber, Lyft any many others available in your country. This platform are part of life for many people. But have your thought of how complicated this platforms can be? Let's just take a look at microservices architecture at uber:

| ![space-1.jpg](https://res.infoq.com/presentations/uber-microservices-distributed-tracing/en/slides/sl9-1567597621813.jpg) | 
|:--:| 
| *Microservices Architecture at Uber* |

If you click a button at Uber, a transaction can hit that architecture which looks like this. It can touch hundreds of nodes, multiple types of surfaces from different business domains. This makes us realise how complicated applications in consumer domain can get over the period. And we are trying to replicate this complexity to some extent in this demo.

HotROD is a demo “ride sharing” application. We have four customers, and by clicking one of the four buttons we summon a car to arrive to the customer’s location, perhaps to pick up a product and deliver it elsewhere. Once a request for a car is sent to the backend, it responds with the car’s license plate number and the expected time of arrival.


### Architecture

| ![space-1.jpg](https://miro.medium.com/max/4596/1*XLxS6v_BWwtU2cvbSsHFyQ.png) | 
|:--:| 
| *Microservices Architecture at Uber* |

### Service Description
| Service                                              | Language      | Description                                                                                                                       |
| ---------------------------------------------------- | ------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| [frontend](./src/frontend)                           | Go            |  The frontend services receives the external HTTP GET request at its /dispatch endpoint. It also makes HTTP GET request to the /customer endpoint of the customer service. At the end, frontend service returns the result to the external caller.|
| [customer](./src/cartservice)                     | Go           | The customer service executes an SQL SELECT statement in MySQL. The results are returned back to the frontend service.                                                           |
| [Driver](./src/productcatalogservice) | Go            | The driver service makes a series of calls to Redis. Some of those calls are highlighted with pink background, indicating failures.                        |
| [Driver:FindNearest](./src/currencyservice)             | Go      | Then the frontend service makes an RPC request Driver::findNearest to the driver service. Without drilling more into the trace details we cannot tell which RPC frameworks is used to make this request, but we can guess it is not HTTP (it is actually made over TChannel). |
| [Route Service](./src/paymentservice)               | Go      | After that the frontend service executes a series of HTTP GET requests to the /route endpoint of the route service.                                    |                                    |


### Get it running

There are two ways you can run this application

1. I just want to get it running way: 
```bash
docker run \
  --rm \
  --env JAEGER_ENDPOINT=http://docker.for.mac.localhost:14268/api/traces \
  -p8080-8083:8080-8083 \
  jaegertracing/example-hotrod:latest \
  all
```

2. I wanna see code and play with it way:
* Clone this repo using ``.
* Run HotROD demo with `docker-compose up`
* Access Hypertrace UI at http://localhost:2020 and HotROD app at http://localhost:8080
* Shutdown / cleanup with `docker-compose down`

Then open http://127.0.0.1:8080 and you can play around with HotROD app!

### Screenshots


| ![space-1.jpg](https://miro.medium.com/max/2000/1*tLGZLrpEE8gz6RZEVJRCbA.png) | 
|:--:| 
| *HotROD Application* |

### Are you facing any issue with this? Let's discuss it here:
