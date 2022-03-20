#FROM java11
FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/book-client-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
EXPOSE 1005
ENTRYPOINT ["java","-jar","/app.jar"]