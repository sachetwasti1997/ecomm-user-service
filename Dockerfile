FROM eclipse-temurin:25-jdk-alpine AS builder
LABEL authors="sachetwasti"

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]