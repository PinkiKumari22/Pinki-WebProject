# ---- Build stage ----
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy project files
COPY demo/pom.xml demo/pom.xml
COPY demo/.mvn demo/.mvn
COPY demo/mvnw demo/mvnw
COPY demo/src demo/src

WORKDIR /app/demo
RUN ./mvnw clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/demo/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
