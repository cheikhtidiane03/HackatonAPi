# Étape 1 : build du .war avec Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# Étape 2 : exécution sur Tomcat 10 (jakarta.*)
FROM tomcat:10.1-jdk17
RUN rm -rf /usr/local/tomcat/webapps/*
# Déployé comme ROOT.war -> pas de préfixe de contexte, endpoints sur /api/... directement
COPY --from=build /app/target/hackathon-backend.war /usr/local/tomcat/webapps/ROOT.war
# Désactive le port d'arrêt interne (8005) — évite toute confusion avec les
# health checks externes (Render, etc.), et empêche un arrêt distant non authentifié
RUN sed -i 's/port="8005"/port="-1"/' /usr/local/tomcat/conf/server.xml
EXPOSE 8080