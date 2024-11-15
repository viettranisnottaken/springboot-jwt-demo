# Use an official OpenJDK as a base image
FROM openjdk:17-ea-17-oracle

# Add a label to identify the maintainer of the image (optional)
LABEL maintainer="tranxuanviet1997@gmail.com"

# Create a directory for the app
WORKDIR /app

# Copy the Spring Boot jar file into the Docker container
COPY build/libs/authDemo-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the app runs on
EXPOSE 8080

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
