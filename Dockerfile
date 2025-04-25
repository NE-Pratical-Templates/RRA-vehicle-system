# Use MAVEN image to build and package the app
FROM maven:3.8.4-openjdk-17 AS builder
# Set working directory
WORKDIR /app

# Copy files
COPY . .

# Build the app anf package it using mvn
RUN mvn clean install

# Create the final image with the packaged JAR
FROM openjdk:17

WORKDIR /app

# Copy the packaged JAR
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8085
EXPOSE 5432
# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]