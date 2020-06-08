It's been a while since we started moving from monolithic applications to microservices based architecture. When you look at systems today you will find that this modern distributed services are large, complex, and increasingly built upon other similarly complex distributed services. 

In this article, we will check out some of the best microservices demo apps!

## A. Online Boutique (previously hipster shop)

### Industry: E-commerce
### Learning objectives:
- How to deploy your application on Kubernetes (both locally on "Docker for Desktop", as well as on the cloud with GKE).
- what is gRPC and How to use gRPC? Microservices use a high volume of gRPC calls to communicate to each other.
- How to get your application to work with istio service mesh.
- What is distributed tracing? Most services in this application are instrumented using OpenCensus trace interceptors for gRPC/HTTP so you can learn about tracing as well. 
- How to use APM?
- How to deploy application to Kubernetes with a single command using Skaffold.
- You will also learn about Synthetic Load Generation as the application demo comes with a background job that creates realistic usage patterns on the website using Locust load generator.

### Introduction

**Online Boutique** is a cloud-native microservices demo application.
Online Boutique consists of a 10-tier microservices application. Online boutique uses different microservices written in Go, c#, Python, Java and functions as an e-commerce website app, where users can browse items,
add them to the cart, and purchase them. 

This demo has various ecommerce microservices like order page, cart, payment, shipping, recommendation though it doesn't operate under the scale as real world ecommerce platform which can have so many reasons to fail but this makes really good close to real-world case for our testing.

If you are interested in complex microservices architectures you should look at structure of microservices at Amazon which look like below!

| ![space-1.jpg](https://divante.com/blog/wp-content/uploads/2019/07/unnamed-5.png) | 
|:--:| 
| *Structure of microservices at Amazon. Looks almost like a Death Star but is way more powerful.* |

`Note:` We have created this project by modifying some configs in [this](https://github.com/GoogleCloudPlatform/microservices-demo) demo app by Google Cloud Platform. 

### Architecture

**Online Boutique** is composed of many microservices written in different
languages that talk to each other over gRPC.

| ![space-1.jpg](https://s3.amazonaws.com/fininity.tech/DT/architecture-diagram.png) | 
|:--:| 
| *Microservices Architecture* |

### Service Description Table

| Service                                              | Language      | Description                                                                                                                       |
| ---------------------------------------------------- | ------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| [frontend](./src/frontend)                           | Go            | Exposes an HTTP server to serve the website. Does not require signup/login and generates session IDs for all users automatically. |
| [cartservice](./src/cartservice)                     | C#            | Stores the items in the user's shopping cart in Redis and retrieves it.                                                           |
| [productcatalogservice](./src/productcatalogservice) | Go            | Provides the list of products from a JSON file and ability to search products and get individual products.                        |
| [currencyservice](./src/currencyservice)             | Node.js       | Converts one money amount to another currency. Uses real values fetched from European Central Bank. It's the highest QPS service. |
| [paymentservice](./src/paymentservice)               | Node.js       | Charges the given credit card info (mock) with the given amount and returns a transaction ID.                                     |
| [shippingservice](./src/shippingservice)             | Go            | Gives shipping cost estimates based on the shopping cart. Ships items to the given address (mock)                                 |
| [emailservice](./src/emailservice)                   | Python        | Sends users an order confirmation email (mock).                                                                                   |
| [checkoutservice](./src/checkoutservice)             | Go            | Retrieves user cart, prepares order and orchestrates the payment, shipping and the email notification.                            |
| [recommendationservice](./src/recommendationservice) | Python        | Recommends other products based on what's given in the cart.                                                                      |
| [adservice](./src/adservice)                         | Java          | Provides text ads based on given context words.                                                                                   |
| [loadgenerator](./src/loadgenerator)                 | Python/Locust | Continuously sends requests imitating realistic user shopping flows to the frontend.                                              |

### Get it running:
Instruction to run app locally using pre-built container images: 

This option offers you pre-built public container images that are easy to deploy
by deploying the [release manifest](./release) directly to an existing cluster.

**Prerequisite**: a running Kubernetes cluster (either local or on cloud).

1. Clone this repository, and go to the repository directory
2. Run `kubectl apply -f ./release/kubernetes-manifests.yaml` to deploy the app.
3. Run `kubectl get pods` to see pods are in a Ready state.
4. Find the `NodePort` of your application, then visit the application at `localhost:nodeport` on your
   browser to confirm installation. 

   ```sh
   kubectl get service/frontend-external
   ```

### Screenshots

| Home Page                                                                                                         | Checkout Screen                                                                                                    |
| ----------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------ |
| [![Screenshot of store homepage](https://raw.githubusercontent.com/JBAhire/HyperTrace-samples/master/Online_Boutique_demo/docs/img/online-boutique-frontend-1.png?token=AGKW2PCHE4XEIIXRO5F5VMS63JCM2)]() | [![Screenshot of checkout screen](https://s3.amazonaws.com/fininity.tech/DT/online-boutique-frontend-2.png)]() |



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
  --link jaeger \
  --env JAEGER_ENDPOINT=http://docker.for.mac.localhost:14268/api/traces \
  -p8080-8083:8080-8083 \
  jaegertracing/example-hotrod:latest \
  all
```

2. I wanna see code and play with it way:
* Clone this repo using ``.
* Run HotROD demo with `docker-compose up`
* Access Hypertrace UI at http://localhost and HotROD app at http://localhost:8080
* Shutdown / cleanup with `docker-compose down`

Then open http://127.0.0.1:8080 and you can play around with HotROD app!

### Screenshots


| ![space-1.jpg](https://miro.medium.com/max/2000/1*tLGZLrpEE8gz6RZEVJRCbA.png) | 
|:--:| 
| *HotROD Application* |



## C. Todo List Application with Auth service

### Industry: Consumer

### Learning objective
- What are the challenges introduced by microservices architectures?
- How to Instrument polyglot microservice application?
- How to evaluate various instruments (monitoring, tracing, you name it): how easy they integrate, do they have any bugs with different languages, etc.?
- How to run microservice application in polyglot environment?
- What are gRPC APIs? How to add authentication to your application?

### Introduction

We looked at two complex systems from two highly consumer facing industries. Let's look at something we all use in our daily lives. This is an example of web application comprising of several components communicating to each other. In other words, this is an example of microservice app. Why is it better than many other examples? Well, because these microservices are written in different languages. This approach gives you flexibility for running experiments in polyglot environment.

The app itself is a simple TODO app that additionally authenticates users. Thanks to [Ivan Kirichenko](https://github.com/elgris) for making this and you can find the fork we used [here](). You can evaluate various instruments (monitoring, tracing, you name it): how easy they integrate, do they have any bugs with different languages, etc.

### Architecture

| ![space-1.jpg](https://user-images.githubusercontent.com/1905821/34918427-a931d84e-f952-11e7-85a0-ace34a2e8edb.png) | 
|:--:| 
| *TODO Application architecture* |

### Service Description
| Service                                              | Language      | Description                                                                                                                       |
| ---------------------------------------------------- | ------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| [frontend](./src/frontend)                           | JS           |  Frontend part is a Javascript application, provides UI. Created with VueJS.|
| [Auth API](./src/cartservice)                     | Go           | Auth API is written in Go and provides authorization functionality. Generates JWT tokens to be used with other APIs.                                                           |
| [TODO API](./src/productcatalogservice) | NodeJS           | TODOs API is written with NodeJS, provides CRUD functionality ove user's todo records. Also, it logs "create" and "delete" operations to Redis queue, so they can be later processed by Log Message Processor.                        |
| [Users API](./src/currencyservice)             | Java      | Users API is a Spring Boot project written in Java. Provides user profiles. Does not provide full CRUD for simplicity, just getting a single user and all users. |
| [Log Message Processor](./src/paymentservice)               | Go      |Log Message Processor is a very short queue processor written in Python. It's sole purpose is to read messages from Redis queue and print them to stdout.                                   |                                    |
| [Zipkin](./src/paymentservice)               | NA      | Optional 3rd party system that aggregates traces produced by other components                                    |                                    |




### Get it running

- Clone the repo using `git clone https://github.com/JBAhire/microservice-app-example`
- Run `docker-compose up`
- Then go to http://127.0.0.1:8080 for web UI. 

### Screenshots

| ![space-1.jpg](https://i.ibb.co/hWp0wN2/Screenshot-2020-06-04-at-10-27-43-AM.png) | 
|:--:| 
| *TODO Application UI* |



## Sock Shop by Weaveworks 

### Industry: E-Commerce

### Learning Objectives:
- Demonstrate microservice best practices (and mistakes!)
- How to be cross-platform i.e. deploy to all orchestrators?
- Show the benefits of continuous integration/deployment
- Demonstrate how dev-ops and microservices compliment each other
- Provide a "real-life" testable application for various orchestration platforms

### Introduction

We have already seen one e-commerce example in our list and we know how complicated microservice architecture of e-commerce apps or websites can get. In this application we will be looking at full-fledged sock store with functinalities including user authentication, catalogue, recommendation, payment and ordering. This application is intended to aid the demonstration and testing of microservice and cloud native technologies. It is built using Spring Boot, Go kit and Node.js and is packaged in Docker containers. 

As you can see in next section, the architecture of the sock store application was intentionally designed to provide as many microservices as possible. It not only helps us to demonstrate microservice best practices (and mistakes!) and how dev-ops and microservices compliment each other but it also provides a "real-life" testable application for various orchestration platforms.

### Architecture

| ![space-1.jpg](https://raw.githubusercontent.com/microservices-demo/microservices-demo.github.io/0ac7e0e579d83ce04e14f3b0942f7a463b72da74/assets/Architecture.png) | 
|:--:| 
| *Sock Shop Architecture* |


### Service Description

| Service                                              | Language      | Description                                                                                                                       |
| ---------------------------------------------------- | ------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| [frontend](./src/frontend)                           | NodeJS           |  Frontend part is a NodeJS application, provides UI and interacts with all other services except shipping using REST over HTTP|
| [User](./src/cartservice)                     | Go           | Provide Customer login, register, retrieval, as well as card and address retrieval. It uses MongoDB to store and retrive information.                                                            |
| [Catalogue](./src/productcatalogservice) | Go          | Catalogue maintains and drisplays catalogue of socks to users. It fetches data from MySQL DB.                         |
| [Order](./src/currencyservice)             | Java      | It processses orders and it interacts with shipping and queue master service.  |
| [Payment](./src/paymentservice)               | Go      |It helps to process payments and provides payment service authentication.                                    |                                    |
| [Cart](./src/paymentservice)               | Go      | Maintains entries user added in cart with the help of MongoDB.                                    |                                    |
| [Shipping](./src/paymentservice)               | NA      | Shipping service sends orders to RabbitMQ and then those go to queue master service.                            |                                    |

### Get it running

1. If you want to deploy on local machine with dockeer-desktop you first need to clone this repo using `git clone "repo"`
2. Run `kubect apply -f complete-demo.yml`
3. Once all your pods are up and running you can visit `http://localhost:30001` to see sock-store app.
4. login using username =`user` and password = `password`. 
5. You are ready to buy some awesome socks from your own store!


### Screenshots

| ![space-1.jpg](https://s3.amazonaws.com/fininity.tech/DT/ss1.png) | 
|:--:| 
| *Sock Shop Home Page* |

| ![space-1.jpg](https://s3.amazonaws.com/fininity.tech/DT/ss1.png) | 
|:--:| 
| *Order Page* |


## What's next?

We hope you enjoyed playing with these *Best Microservices Sample Apps* and learnt about microservice architecture from this apps. One of the biggest benefits of microservices is that each microservice can be developed, scaled, and deployed separately.You can replace or upgrade any part of system independently. While this makes things more modular one thing you might have observed is it also makes system much more complex at the same time. You can't simply go ahead and use traditional machine-centric monitoring and tracing mechanisms for management and development tasks in such a complex environments specifically because they are not effective and they cannot provide a coherent view of the work done by a distributed service’s nodes and dependencies. Because of this, tools that aid in understanding system behavior and reasoning about performance issues are invaluable in such a complex environment.
There are multiple tools developed by multiple communities and developers to solve this problem and we will discuss about one such important tool in future but meanwhile, if you want to know more or have doubts about any of this application or you need any help with your own microservice architecture you can join this slack channel and we will be glad to help you out! 