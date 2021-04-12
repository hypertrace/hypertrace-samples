#!/usr/bin/env bash
set -eu
ENDPOINT=${TRACE_EXPORTER_END_POINT:-"localhost:9411"}
sed -i -e 's/TRACE_EXPORTER_END_POINT/'"${ENDPOINT}"'/g' config.yaml
java -javaagent:/app/hypertrace-agent-all.jar -jar /app/libs/async-kafka-consumer-1.0.jar

