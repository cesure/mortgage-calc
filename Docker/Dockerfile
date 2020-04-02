FROM openjdk:11-jre-slim

RUN groupadd -r mortgage-calc && useradd -r -g mortgage-calc mortgage-calc
USER mortgage-calc:mortgage-calc

ARG JAR_FILE=build/*-all.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
