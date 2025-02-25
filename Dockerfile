FROM gradle:7.5 AS build

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

FROM openjdk:17.0.2-slim-bullseye

COPY --from=build /app/build/libs/fiap-tech-challenge-0.0.1-SNAPSHOT.jar app.jar

RUN apt-get update && apt-get install -y curl

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]