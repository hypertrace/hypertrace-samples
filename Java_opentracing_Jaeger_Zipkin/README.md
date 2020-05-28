This is Java OpenTracing App which send traces to zipkin and Jaeger.

## Steps:
1. Make sure you are able to access `hypertrace`.
2. If you're using any IDE just run the main application.
3. go to `http://localhost:8080/hello` and refresh 2-3 times or send curl requests.
4. Now check in hypertrace UI if you're able to see traces.

## Changes:
1. As we shifted to LoadBalancer, there are no changes required here. Just run the app and this works out of the box with hypertrace.
