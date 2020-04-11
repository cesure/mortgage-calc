FROM openjdk:11-jdk-slim AS build-env

# First install Gradle Wrapper
COPY gradlew /app/
COPY gradle /app/gradle
WORKDIR /app
RUN ./gradlew --no-deamon wrapper

# Then build the app
COPY . /app/
RUN ./gradlew --no-daemon build

FROM gcr.io/distroless/java:11
COPY --from=build-env /app/backend/build/libs/*-all.jar /app/app.jar
WORKDIR /app
CMD ["app.jar"]
