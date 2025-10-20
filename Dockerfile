FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app

# Create logs directory
RUN mkdir -p /app/logs

# Create user for security
RUN groupadd -r spring && useradd -r -g spring spring
USER spring

# Copy jar file
COPY --from=builder /app/target/*.jar app.jar

# Expose port from environment or default to 8080
ENV SERVER_PORT=8080
EXPOSE ${SERVER_PORT}

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:${SERVER_PORT}/api/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]