The best part in getting started with Hypertrace is that it's really quick! If you are already using a tracing system, you can start today. Hypertrace accepts all major data formats: Jaeger, OpenTracing, Zipkin, you name it. Even if you aren’t tracing yet, we have a bunch of sample apps you can start with, and a [chat room](https://hypertrace.slack.com) of excited people who want to meet you. Here we will tell you how you can get started with Online Boutique sample app which is one of our trace enabled sample applications.

### Sample app: Online Boutique (created by Google Cloud)

Online Boutique is one of our trace enabled sample applications. It includes typical ecommerce functionality, including a product catalog and a way for customers to check out in different currencies This application uses different languages to highlight the diversity in micro service architecture: Golang, C++, C#, Python, Java and other programming languages. Whatever your application is written in, you can see its requests in Hypertrace.

If you want to start your own online boutique, sorry! This doesn’t include authentication, credit card processing and features in the real world! However, we can use this to understand hypertrace and get you started with distributed tracing. 

#### Deployment instructions

Use pre-built public container images that are easy to deploy by deploying the [release manifest](./release) directly to an existing K8s cluster.

**Prerequisite**: A running Kubernetes cluster (local or cloud).

1. `git clone https://github.com/hypertrace/hypertrace-samples.git`
2. `cd online-boutique-demo`
2. Run `kubectl apply -f ./release/kubernetes-manifests.yaml` to deploy the sample app.
3. Run `kubectl get pods` to confirm pods are in a Ready state.
4. Find the `NodePort` of your application, then visit the application at `localhost:nodeport` in your
   browser to confirm installation. 

   ```sh
   kubectl get service/frontend-external
   ```

#### This is how your application will look like!

| Home Page                                                                                                         | Checkout Screen                                                                                                    |
| ----------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------ |
| [![Screenshot of store homepage](https://s3.amazonaws.com/fininity.tech/online-boutique-frontend-1-min.png)]() | [![Screenshot of checkout screen](https://s3.amazonaws.com/fininity.tech/DT/online-boutique-frontend-2.png)]() |


#### This is how your tracing data will look like on Hypertrace! 

You can check out [UI & Platform overview](https://hypertrace-docs.netlify.app/docs/platform-ui/) section to get more details on features and see insights of online boutique app using Hypertrace. 

## More information about Online Boutique application!
#### Architecture

**Online Boutique** is composed of many microservices written in different languages that talk to each other over gRPC.

| ![space-1.jpg](https://s3.amazonaws.com/fininity.tech/DT/architecture-diagram.png) | 
|:--:| 
| *Microservices Architecture* |

### Service Description Table

| Service                                              | Language      | Description                                                                                                                       |
| ---------------------------------------------------- | ------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| [frontend](./online-boutique-demo/src/frontend)                           | Go            | Exposes an HTTP server to serve the website. Does not require signup/login and generates session IDs for all users automatically. |
| [cartservice](./online-boutique-demo/src/cartservice)                     | C#            | Stores the items in the user's shopping cart in Redis and retrieves it.                                                           |
| [productcatalogservice](./online-boutique-demo/src/productcatalogservice) | Go            | Provides the list of products from a JSON file and ability to search products and get individual products.                        |
| [currencyservice](./online-boutique-demo/src/currencyservice)             | Node.js       | Converts one currency to another. Uses real values fetched from European Central Bank. It's the highest QPS service. |
| [paymentservice](./online-boutique-demo/src/paymentservice)               | Node.js       | Charges the given credit card (mock) with the given amount and returns a transaction ID.                                     |
| [shippingservice](./online-boutique-demo/src/shippingservice)             | Go            | Gives shipping cost estimates based on the shopping cart items. Ships items to the given address (mock)                                 |
| [emailservice](./online-boutique-demo/src/emailservice)                   | Python        | Sends users an order confirmation email (mock).                                                                                   |
| [checkoutservice](./online-boutique-demo/src/checkoutservice)             | Go            | Retrieves user cart, prepares order and orchestrates the payment, shipping and the email notification.                            |
| [recommendationservice](./online-boutique-demo/src/recommendationservice) | Python        | Recommends other products based on shopping cart items.                                                                      |
| [adservice](./online-boutique-demo/src/adservice)                         | Java          | Provides text ads based on given context words.                                                                                   |
| [loadgenerator](./online-boutique-demo/src/loadgenerator)                 | Python/Locust | Continuously sends requests imitating realistic user shopping flows to the frontend.                                              |




### Note: 
- Are you facing any issue with this? Let's discuss it on [slack](https://hypertrace.slack.com)
- If you want to try more apps you can try apps from this sample apps repo!

### Other samples:
1. [Horod application](/hotrod/README.md)
2. [todo-list-application](/todo-list-application/README.md)



Are you still confused with **Instrumentation** jargon? Ahh! We have you covered! Jump to [Instrumentation](https://hypertrace-docs.netlify.app/docs/getting-started/instrumentation/) section which will tell you about what is instrumentation and how you can get started with instrumenting your application! 