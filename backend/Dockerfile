FROM maven:3.6.3-jdk-11 AS build-env
WORKDIR /app
COPY . /app
RUN mvn prepare-package
RUN mvn package
 
FROM openjdk:11-jre-slim
WORKDIR /app

EXPOSE 8080

COPY --from=build-env /app/target/*.jar service.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/service.jar"]