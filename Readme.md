### Example of dropwizard based web service
    1. Server - Jetty
    2. DI - google guice
    3. REST - Jersey
    4. DAO - JDBI3
    5. Build tool - gradle
    
# Run
    1. > gradlew shadowJar 
    2. > java -jar build/libs/dropwizard-guice-jdbi-1.0-SNAPSHOT-all.jar server config/app-config.yml
    
    Postgres DB required. You can override by passing env variables:
    ${POSTGRES_HOST:-localhost}
    ${POSTGRES_PORT:-32767}
    Default is localhost:32767
    
    or
    1. > gradlew shadowJar 
    2. > docker build --tag=dropwizard-guice-jdbi:latest .
    3. > docker run -p 8080:8080 -e POSTGRES_HOST=host -e POSTGRES_PORT=port dropwizard-guice-jdbi


