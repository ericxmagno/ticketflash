# Stage 1: Build the Spring Boot Application
FROM maven:3.9-eclipse-temurin-21 AS booking-service-builder

# Set the working directory inside the container
WORKDIR /app

# Copy the Root POM and the pom for the two modules we need to build
# The pathing here is relative to the Docker context (the root of the microservice directory)
COPY pom.xml ./
COPY ticketflash-common/pom.xml ticketflash-common/
COPY ticketflash-booking-service/pom.xml ticketflash-booking-service/

# Copy source code for the common module (so Booking can import its classes)
COPY ticketflash-common/src ticketflash-common/src

# Copy the source code for the booking service itself
COPY ticketflash-booking-service/src ticketflash-booking-service/src

# Run the Maven package command to build the JAR
# -DskipTests: Speeds up the build
# -pl ticketflash-booking-service: Explicitly builds only this module and its required dependencies
RUN mvn clean package -DskipTests -pl ticketflash-booking-service -am 

# Stage 2: Create the final, minimal JRE image
FROM maven:3.9-eclipse-temurin-21
EXPOSE 8081

# Create a non-root user for security
RUN groupadd spring && useradd spring -g spring
USER spring

# Copy the built JAR from the builder stage
COPY --from=booking-service-builder /app/ticketflash-booking-service/target/ticketflash-booking-service-0.0.1-SNAPSHOT.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]