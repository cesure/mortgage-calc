# Mortgage Calc

## About

_Mortgage Calc_ is a small web application to calculate house mortgages with different parameters.

It was started as a hobby project because I wanted to try out some new-to-me technologies:
* [ktor](https://github.com/ktorio/ktor) for the backend,
* [Typescript](https://github.com/Microsoft/TypeScript)
* and [Vue.js](https://github.com/vuejs) for the frontend.


## Build

### Build with Gradle

You can build the project by running `./gradlew build` in the project root.
This will create a _fat/uber JAR_ in `backend/build/libs/mortgage-calc-*-all.jar` which contains the backend and frontend. 

The JAR can be started by `java -jar path/to/mortgage-calc-*-all.jar`
and the app can be reached under port 8080, e.g. http://localhost:8080.

You will need at least Java 11 to build and run the application.

### Build with Docker

This project can be built and run entirely with Docker.
You can use the shipped [docker-compose.yml](docker-compose.yml) and simply start everything by `docker-compose up`
and the app is by default reachable under port 8080, e.g. http://localhost:8080.


## License

This project is licensed under the [MIT License](LICENSE).
