# FROM maven:3.3.9-jdk-8 as build
FROM openjdk:8

WORKDIR /usr/src

COPY "OnlinePong-1.0.jar" .
COPY webapp webapp

# RUN mvn install -Dskiptests

CMD ["java", "-jar", "OnlinePong-1.0.jar"]

EXPOSE 20000

