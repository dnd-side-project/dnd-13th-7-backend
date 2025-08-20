FROM eclipse-temurin:21
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar


RUN mkdir -p /opt/newrelic
ADD ./newrelic/newrelic.jar /opt/newrelic/newrelic.jar
ADD ./newrelic/newrelic.yml /opt/newrelic/newrelic.yml

ENTRYPOINT ["java","-javaagent:/opt/newrelic/newrelic.jar","-jar","/app.jar","--spring.profiles.active=dev"]
