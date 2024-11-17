# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Make the port 8080 available to the world outside this container
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "demo.jar"]
