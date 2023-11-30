# Creates the runnable .jar file using Maven
FROM maven:latest AS builder
ENV HOME=/src
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN mvn clean package

# Creates container which runs the created .jar file
FROM amazoncorretto:20-alpine-jdk
ARG JAR_FILE=/src/target/*-jar-with-dependencies.jar
COPY --from=builder $JAR_FILE /app/runner.jar

COPY key.json /app/key.json
ENV GOOGLE_APPLICATION_CREDENTIALS /app/key.json
ENTRYPOINT java -jar /app/runner.jar