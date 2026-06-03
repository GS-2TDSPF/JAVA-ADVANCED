# ============================================================
# OrbitAlert — Dockerfile
# Multi-stage build: Maven build + JRE 21 runtime
# ============================================================

# Stage 1 — Build com Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia pom.xml primeiro para aproveitar cache de dependências
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código fonte e compila
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2 — Runtime com JRE mínimo
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copia o .jar gerado no stage de build
COPY --from=build /app/target/orbitAlert-0.0.1-SNAPSHOT.jar app.jar

# Porta exposta
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]
