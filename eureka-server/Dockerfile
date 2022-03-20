#FROM java11
FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/eureka-server-1.0.jar
COPY ${JAR_FILE} /app.jar
EXPOSE 1001
ENTRYPOINT ["java","-jar","/app.jar"]