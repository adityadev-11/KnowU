# ── Stage 1: Build the Spring Boot backend ──
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copy Maven files first (for layer caching)
COPY backend/pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

# Copy backend source
COPY backend/src ./src

# Copy frontend into Spring Boot static resources
COPY frontend/ ./src/main/resources/static/

# Build the JAR (skip tests for faster build)
RUN mvn clean package -DskipTests -B


# ── Stage 2: Run ──
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from Stage 1
COPY --from=build /app/target/*.jar app.jar

# Render uses PORT env variable (default 10000)
ENV PORT=10000

EXPOSE ${PORT}

# Run Spring Boot — Spring will pick up PORT from env via application.properties
ENTRYPOINT ["java", "-jar", "app.jar"]
