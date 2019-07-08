FROM openjdk:8-jre-alpine
MAINTAINER Eugene Moiseev

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.jar", "server" ,"/usr/share/myservice/app-config.yml"]


# Add the service itself
ENV JAR_FILE dropwizard-guice-jdbi-1.0-SNAPSHOT-all.jar
ENV APP_CONFIG app-config.yml

COPY config/${APP_CONFIG} /usr/share/myservice/app-config.yml
COPY build/libs/${JAR_FILE} /usr/share/myservice/myservice.jar