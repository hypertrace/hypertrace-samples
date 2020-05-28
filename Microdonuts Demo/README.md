# MicroDonuts: An OpenTracing Walkthrough

Welcome to MicroDonuts! This is a sample application and OpenTracing
walkthrough, written in Java.

OpenTracing is a vendor-neutral, open standard for distributed tracing. To
learn more, check out [opentracing.io](http://opentracing.io), and try the
walkthrough below!



## Step 0: Setup MicroDonuts

### Getting it
Clone this repository and build the jar file (for this, Maven must be
installed):

```
git clone git@github.com:opentracing-contrib/java-opentracing-walkthrough.git
cd java-opentracing-walkthrough/microdonuts
mvn package
```

### Running

MicroDonuts has two server components, `API` and `Kitchen`, which
communicate each other over HTTP - they are, however, part of
the same process:

```
cd java-opentracing-walkthrough/microdonuts
mvn package exec:exec
```

In your web browser, navigate to http://127.0.0.1:10001 and order yourself some
µ-donuts.

### Pick a Tracer

Several OpenTracing-compatible Tracer implementations are supported
out-of-the-box for convenience. Others can be added easily with a local change
to `App.java`.

This provides an option to use Jaeger or Zipkin but Jaeger configuration requires Agent Host and Port and it doesn't use endpoint. So We will stick to Zipkin. 

Note that the all-in-one docker image presents the Jaeger UI at [localhost:16686](http://localhost:16686/).

#### Zipkin

Then add the following to `microdonuts/tracer_config.properties`:

```properties
tracer=zipkin
zipkin.reporter_host=localhost
zipkin.reporter_port=9411
```

### Start the App:
1. Run using any IDE or Terminal.
2. visit `localhost:10001` and order your donuts.
3. check if you are able to see traces on hypertrace platform by the times donuts are ready!
4. Check the documentation [here](https://github.com/opentracing-contrib/java-opentracing-walkthrough/blob/master/README.md) if you want to add some other tracer or modify something. 

