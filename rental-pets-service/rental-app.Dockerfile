FROM openjdk:17-jdk-slim

WORKDIR /app
COPY rental-pets-service/target/rental-pets-service-0.0.1-SNAPSHOT.jar /app/rental-pets-service.jar

COPY rental-pets-service/src/main/resources/application.yml /app/application.yml

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "rental-pets-service.jar"]
CMD ["--spring.config.location=file:/app/application.yml"]