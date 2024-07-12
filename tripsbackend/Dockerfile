# First stage: Build the application 
FROM openjdk:21-jdk-slim AS build 
# Install Maven 
RUN apt-get update && \ 
apt-get install -y maven && \ 
rm -rf /var/lib/apt/lists/* 
# Set the working directory 
WORKDIR /app 
# Copy the pom.xml file and download dependencies 
COPY pom.xml . 
RUN mvn dependency:go-offline 
# Copy the entire project and build it 
COPY src ./src 
RUN mvn package -DskipTests 
# Second stage: Create a smaller runtime image 
FROM openjdk:21-jdk-slim 
# Set the working directory 
WORKDIR /app 
# Copy the JAR file from the build stage 
COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar ./demo.jar
# Expose the application port (optional) 
EXPOSE 8080 
# Define the entry point for the container 
ENTRYPOINT ["java", "-jar", "demo.jar"]
