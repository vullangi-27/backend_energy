# Multi-stage Docker build for Spring Boot (Java 21)
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copy Maven wrapper files and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Download dependencies offline
RUN ./mvnw dependency:go-offline -B

# Copy application source code and build jar
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# Runtime container using lightweight JRE
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]
