# Use OpenJDK base image
FROM openjdk:20-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the Pyroscope agent into the container (from the local directory)
COPY pyroscope.jar /pyroscope/pyroscope.jar

# Copy the Spring Boot JAR file into the container
COPY target/auth-service-0.0.1-SNAPSHOT.jar /app/auth-service.jar

# Expose port 8081 (for your Spring Boot application)
EXPOSE 8081

# Command to run the Spring Boot application
CMD ["java", "-javaagent:/pyroscope/pyroscope.jar", "-Dpyroscope.server.address=https://profiles-prod-018.grafana.net", "-Dpyroscope.auth.token=", "-Dpyroscope.application.name=auth-service", "-jar", "auth-service.jar"]
