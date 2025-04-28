
FROM openjdk:21-jdk-slim AS build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

COPY src src
RUN chmod +x ./gradlew && \
    ./gradlew clean && \
    ./gradlew build --exclude-task test

RUN rm -rf /root/.gradle

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /workspace/app/build/libs/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]
