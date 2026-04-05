# ETAPA 1 - BUILD
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

WORKDIR /app/todolist
RUN mvn clean package -DskipTests

# ETAPA 2 - RUN
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/todolist/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]