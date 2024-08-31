FROM openjdk:17-jdk-slim

WORKDIR /app

COPY admin-server-service/target/admin-server-service-0.0.1-SNAPSHOT.jar /app/spring-boot-admin-server.jar

EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/spring-boot-admin-server.jar"]