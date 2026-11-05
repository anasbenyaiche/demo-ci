
# Étape 1 : compilation avec Maven
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn -B clean package

# Étape 2 : image d'exécution
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/demo-ci-1.0.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]