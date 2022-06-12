FROM maven:3.6.0-jdk-11-slim as base
WORKDIR /root/.m2/
WORKDIR /app
COPY . .
RUN mvn test

#### Stage 1 - build
#FROM openjdk:17-alpine as base
#WORKDIR /root/.m2/
#WORKDIR /build
#COPY . .
#RUN mvn package
#
#### Stage 2 - copy app
#FROM openjdk:17-alpine
#WORKDIR /app
#COPY --from=base build/target/shop-0.0.1-SNAPSHOT.jar /app
## Use this volume to pass spring application.yml
#VOLUME ["/config"]
## Use this volume to set directory for logs
#VOLUME ["/logs"]
## Map this part when running application
#EXPOSE 8083
#
#### Stage 3 - run
#ENTRYPOINT ["java", "-jar", "/shop-0.0.1-SNAPSHOT.jar"]
