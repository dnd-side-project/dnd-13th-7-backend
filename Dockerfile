FROM eclipse-temurin:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar


RUN mkdir -p /usr/local/newrelic
ADD ./newrelic/newrelic.jar /usr/local/newrelic/newrelic.jar
ADD ./newrelic/newrelic.yml /usr/local/newrelic/newrelic.yml


ENTRYPOINT ["java","-javaagent:/opt/newrelic/newrelic.jar","-jar","/app.jar","--spring.profiles.active=dev"]
