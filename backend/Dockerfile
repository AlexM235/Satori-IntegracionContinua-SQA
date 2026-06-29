# ETAPA 1: Construcción (Maven con Java 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el pom.xml y descargamos dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Entorno ligero con Java 21)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copiamos SOLO el archivo .jar
COPY --from=build /app/target/*.jar app.jar

# Creamos el directorio para las imágenes subidas
RUN mkdir uploads

# Exponemos el puerto de Spring Boot
EXPOSE 8080

# Comando para arrancar la app
ENTRYPOINT ["java", "-jar", "app.jar"]