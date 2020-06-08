## C. Todo List Application with Auth service

### Industry: Consumer

### Learning objectives
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

### Are you facing any issue with this? Let's discuss it here: 
