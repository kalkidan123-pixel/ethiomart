FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY shared-events shared-events
COPY auth-service auth-service
COPY order-service order-service
COPY payment-service payment-service
COPY inventory-service inventory-service
COPY shipping-service shipping-service
COPY notification-service notification-service
COPY e2e-tests e2e-tests
RUN mvn -pl auth-service -am package -DskipTests -q

FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY --from=build /app/auth-service/target/auth-service-1.0.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "app.jar"]
