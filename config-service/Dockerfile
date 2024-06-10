FROM openjdk:17
LABEL authors="alejandrocortes"
ARG JAR_FILE=build/libs/*1.jar
COPY ${JAR_FILE} config-service.jar
ENTRYPOINT ["java", "-jar", "/config-service.jar"]