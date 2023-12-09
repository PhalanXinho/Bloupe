FROM amazoncorretto:20-alpine-jdk

COPY target/*-jar-with-dependencies.jar /app.jar
COPY key.json /app/key.json

ENV GOOGLE_APPLICATION_CREDENTIALS /app/key.json
EXPOSE 4567
ENTRYPOINT java -jar /app.jar