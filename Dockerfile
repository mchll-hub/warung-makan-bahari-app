FROM openjdk:17-jdk-alpine
MAINTAINER enigmacamp.com
COPY target/warung-makan-bahari-api-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]