FROM openjdk:8-jre-alpine

LABEL maintainer=Eugene_Moiseev

ARG JAR_FILE=build/libs/dropwizard-guice-jdbi-1.0-SNAPSHOT-all.jar
ARG APP_CONFIG=config/app-config.yml

COPY ${APP_CONFIG} /usr/share/myservice/app-config.yml
COPY ${JAR_FILE} /usr/share/myservice/myservice.jar

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/myservice/myservice.jar", "server" ]
CMD ["/usr/share/myservice/app-config.yml"]
