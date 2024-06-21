# Use Maven to build the project
FROM maven:3.8.4-openjdk-17 AS maven-builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
 
# Use a minimal Java runtime for the final image
FROM openjdk:17-alpine
WORKDIR /app-service
COPY --from=maven-builder /app/target/SpringBootBusinessTripsv2-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "SpringBootBusinessTripsv2-0.0.1-SNAPSHOT.jar"]