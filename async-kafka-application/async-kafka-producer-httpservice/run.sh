#!/usr/bin/env bash
set -eu
ENDPOINT=${TRACE_EXPORTER_END_POINT:-"http://localhost:9411/api/v2/spans"}
NAME=${SERVICE_NAME:-"async-kafka-producer-httpservice"}

# prepare config.yaml file
text=''
text=$text"service_name: ${NAME}\n"
text=$text"reporting:\n"
text=$text"  endpoint: ${ENDPOINT}\n"
echo -e $text > config.yaml

java -javaagent:/app/hypertrace-agent-all.jar -jar ./libs/async-kafka-producer-httpservice-1.0.jar