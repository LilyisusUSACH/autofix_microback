FROM openjdk:17
LABEL authors="alejandrocortes"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} micro2.jar
ENTRYPOINT ["java", "-jar", "/micro2.jar"]