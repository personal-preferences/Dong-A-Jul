# Step 1: Use a base image with Java 17
FROM openjdk:17-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the build files to the container
COPY build/libs/user_service-0.0.1-SNAPSHOT.jar /app/application.jar

# Step 4: Expose the application port

# Step 5: Define the command to run the application
CMD ["java", "-jar","-Xmx512m", "/app/application.jar"]
