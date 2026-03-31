# Build stage
FROM gradle:8.14.2-jdk17 AS build
WORKDIR /app

COPY . .
RUN gradle build -x test

# Run stage
FROM eclipse-temurin:21-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "echo 'Waiting for DB...' && sleep 25 && java -jar app.jar"]