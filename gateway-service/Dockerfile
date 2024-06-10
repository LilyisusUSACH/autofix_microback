FROM openjdk:17
LABEL authors="alejandrocortes"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} gateway-service.jar
ENTRYPOINT ["java", "-jar", "/gateway-service.jar"]