# Best Microservice sample apps

It's been a while since we started moving from monolithic applications to microservices based architecture. When you look at systems today you will find that this modern distributed services are large, complex, and increasingly built upon other similarly complex distributed services. 

Let's first start with understanding one basic microservice app which will give us fair idea about what we are looking at and what microservice architecture means. 

### Learning objectives
- Learn to create some python flask api based microservices and make them call each other. 
- Learn to instrument basic microservice app
- Learn to deploy microservice application with kubernetes. 

### Tools we will be using
1. Python
2. Flask: Flask is a popular, extensible web microframework for building web applications with Python.
3. Kubernetes

### Let's understand application flow and code

This application is based on sample application available [here](https://github.com/MohamedMSaeed/tracing_flask_zipkin). 

Architecture for our final application will look something like below:

![](https://miro.medium.com/max/1400/1*fJBmBYPBrCVwbbfDucCmtw.png)

#### How it works?

We have 3 flask API's here. API-1 communicates with API-2 and API-3 and API-2 talks to API-3. We are trying to implement classic microservices scenario here. Let's look a bit into those API's:

1. API-1

```python
@app.before_request
def log_request_info():
    app.logger.debug('Headers: %s', request.headers)
    app.logger.debug('Body: %s', request.get_data())


@zipkin_client_span(service_name='api_01', span_name='call_api_02')
def call_api_02():
    headers = create_http_headers()
    # k8s does not allow underscores in the service name
    requests.get('http://api-02:5000/', headers=headers)
    return 'OK'


@zipkin_client_span(service_name='api_01', span_name='call_api_03_FROM_01')
def call_api_03():
    headers = create_http_headers()
    requests.get('http://api-03:5000/', headers=headers)
    return 'OK'

```

API-1 here as expected accepts the requests and calls API-2 and API-3 as expected.

2. API-2

```python

@zipkin_client_span(service_name='api_02', span_name='call_api_03')
def call_api_03():
    headers = create_http_headers()
    requests.get('http://api-03:5000/', headers=headers)
    return 'OK'

```

API-2 here calls API-3.

3. API-3

```python
@zipkin_client_span(service_name='api_03', span_name='sleep_api_03')
def sleep():
    time.sleep(2)
    return 'OK'

```

API-3 is very lazy and it just sleeps!


So, Now we have flask apps and we know the flow, how should we deply our apps?

Let's create a dockerfile which will help us to build docker container to run the app!

```python
From python:3.7

COPY ./requirements.txt /app/requirements.txt

WORKDIR /app

RUN pip install -r requirements.txt

COPY . /app

ENTRYPOINT [ "python" ]

CMD [ "app.py" ]
```

once we run docker build for each app we will get docker images for each microservice or API. We can create a kubernetes deployment with this apps and get this app runnning with kubernetes but first thing we need for this is yaml file. 

Let's start creating yaml file:

```python 
apiVersion: v1
kind: Namespace
metadata:
  name: simple-python

# app_01
---
apiVersion: apps/v1
kind: Deployment
metadata:
  name: simplepython
  namespace: simple-python
spec:
  replicas: 1
  selector:
    matchLabels:
      app: simplepython
  template:
    metadata:
      labels:
        app: simplepython
    spec:
      containers:
      - name: simplepython
        image: python_app_01:0.1.0
---
apiVersion: v1
kind: Service
metadata:
  name: api-01
  namespace: simple-python
spec:
  type: ClusterIP
  selector:
    app: simplepython
  ports:
  - name: http
    port: 5000
    targetPort: 5000
---
apiVersion: v1
kind: Service
metadata:
  name: simplepython-external
  namespace: simple-python
spec:
  type: LoadBalancer
  selector:
    app: simplepython
  ports:
  - name: simplepython-external-port
    port: 5001
    targetPort: 5000
```

Similary you can add API-2 and API-3 and you have your yaml file ready.

Now just go to your console and run `kubectl apply -f deploy.yaml` once all pods are up and running access API-1 at `localhost:5001` and you have successfully deployed your first microservice app!

Was it fun? 

Learn more about microservices at microservices.io and if you want to checkout more cool and complex samples you can visit our colletion of best microservices below:

1. [Online Boutique Demo](https://github.com/JBAhire/HyperTrace-samples/blob/master/blog/onlineboutique.md)
2. [Sock shop demo](https://github.com/JBAhire/HyperTrace-samples/blob/master/blog/sockshop.md)
3. [TODO list demo](https://github.com/JBAhire/HyperTrace-samples/blob/master/blog/todolist.md)
4. [HotROD app](https://github.com/JBAhire/HyperTrace-samples/blob/master/blog/hotrod.md)


## What's next?

We hope you enjoyed playing with these *Best Microservices Sample Apps* and learnt about microservice architecture from this apps. One of the biggest benefits of microservices is that each microservice can be developed, scaled, and deployed separately.You can replace or upgrade any part of system independently. While this makes things more modular one thing you might have observed is it also makes system much more complex at the same time. You can't simply go ahead and use traditional machine-centric monitoring and tracing mechanisms for management and development tasks in such a complex environments specifically because they are not effective and they cannot provide a coherent view of the work done by a distributed service’s nodes and dependencies. Because of this, tools that aid in understanding system behavior and reasoning about performance issues are invaluable in such a complex environment.

There are multiple tools developed by multiple communities and developers to solve this problem and we will discuss about one such important tool in future but meanwhile, if you want to know more or have doubts about any of this application or you need any help with your own microservice architecture you can join this slack channel and we will be glad to help you out! 
