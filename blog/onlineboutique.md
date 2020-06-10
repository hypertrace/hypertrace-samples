## A. Online Boutique (previously hipster shop)

### Industry: E-commerce

### Created by: [Google cloud platform](https://github.com/GoogleCloudPlatform/microservices-demo)

### Learning objectives:
- How to create polyglot microservices app?
- Unerstanding patterns and complexity of polyglot app.
- Learn to create production-ready deployments.
- Learn to deploy your application on Kubernetes (both locally on "Docker for Desktop", as well as on the cloud with GKE).
- Learn to troubleshoot the app for issues.



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


### Microservices patterns:
1. API Gateway pattern
2. Observability patterns:
   - Distributed tracing
   - Log aggregation
   - Application metrics
   - Health check API
3. Deployment patterns:
   - Service mesh
   - Container
4. Discovery
   - Service registry
5. Data pattern
   - Shared database
6. UI Patterns
   - client-side UI composition
7. Testing
   - Service component test

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
| [![Screenshot of store homepage](https://raw.githubusercontent.com/JBAhire/HyperTrace-samples/master/Online%20Botique%20Demo/docs/img/online-boutique-frontend-1.png?token=AGKW2PGEOOH4A56673JCDHS646BIO)]() | [![Screenshot of checkout screen](https://s3.amazonaws.com/fininity.tech/DT/online-boutique-frontend-2.png)]() |

### Are you facing any issue with this? Let's discuss it here:
