## Sample application for async trace

Build
- run the command `./gradlew jar`
- run the command to prepare docker file `docker build ./`

Running
- use docker-compose file provided in docker folder and run
- change the ENV `TRACE_EXPORTER_END_POINT` for your appropriate endpoint
- `docker-compose -f docker-compose.yaml up`
