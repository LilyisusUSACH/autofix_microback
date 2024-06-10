FROM openjdk:17
LABEL authors="alejandrocortes"
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} micro4.jar
ENTRYPOINT ["java", "-jar", "/micro4.jar"]