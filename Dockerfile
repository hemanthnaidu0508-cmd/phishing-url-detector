# Use Java 17 runtime
FROM eclipse-temurin:17-jre

# Create app directory
WORKDIR /app

# Copy jar from target folder
COPY target/*.jar app.jar

# Expose port (Render will override this)
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
