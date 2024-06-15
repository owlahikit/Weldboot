
##
## Build stage
##
#FROM maven:3.8.1-openjdk-17 AS build
#VOLUME /root/.m2
#COPY . .
#RUN mvn clean package -DskipTests
#
##
## Package stage
##
#FROM openjdk:17-alpine
#COPY --from=build /target/weldbootmvn-0.0.1-SNAPSHOT.jar weldbootmvn.jar
##ENV PORT 8080
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "weldbootmvn.jar"]

FROM maven:3.9.7-eclipse-temurin-21-alpine
WORKDIR /opt/app
COPY mvnw pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM openjdk:21-slim
WORKDIR /opt/app
EXPOSE 8081
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]