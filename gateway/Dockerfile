#FROM java11
FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/gateway-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
EXPOSE 1004
ENTRYPOINT ["java","-jar","/app.jar"]