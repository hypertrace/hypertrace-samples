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

| ![space-1.jpg](https://s3.amazonaws.com/fininity.tech/DT/ss2.png) | 
|:--:| 
| *Order Page* |

### Are you facing any issue with this? Let's discuss it here:
