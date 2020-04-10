FROM openjdk:11-jdk-slim AS build-env
ADD . /app
WORKDIR /app
RUN ./gradlew --no-daemon build

FROM gcr.io/distroless/java:11
COPY --from=build-env /app/backend/build/libs/*-all.jar /app/app.jar
WORKDIR /app
CMD ["app.jar"]
