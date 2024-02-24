# ARG VERSION=1.0
# ARG HYPERTRACE_AGENT_VERSION=1.0.5

FROM openjdk:11-jdk-buster
WORKDIR /app
RUN wget https://github.com/hypertrace/javaagent/releases/download/1.0.5/hypertrace-agent-all.jar && ls .
COPY build/libs libs/
COPY build/resources resources/
COPY config.yaml config.yaml
COPY run.sh run.sh
RUN chmod a+x run.sh

ENV HT_CONFIG_FILE=config.yaml
ENV SERVICE_NAME=async-kafka-consumer
ENV KAFKA_BOOTSTRAP_SERVERS=localhost:9092
ENV TRACE_EXPORTER_END_POINT=localhost:9411
EXPOSE ${PORT}

#ENTRYPOINT ["java", "-javaagent:/app/hypertrace-agent-all.jar", "-jar", "./libs/async-kafka-consumer-1.0.jar"]
ENTRYPOINT ["./run.sh"]