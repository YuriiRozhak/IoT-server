# 1️⃣ Build React UI
FROM node:18 AS frontend-build
WORKDIR /app
COPY frontend /app
RUN npm install && npm run build

# 2️⃣ Build Spring Boot Backend
FROM maven:3.8.4-openjdk-17 AS backend-build
ARG API_KEY
WORKDIR /app
COPY --from=frontend-build /app/build /app/src/main/resources/static/
COPY . /app
RUN sed -i "s/API-KEY/${API_KEY}/g" /app/src/main/resources/application.properties
RUN mvn package -DskipTests

# 3️⃣ Run the Application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=backend-build /app/target/*.jar /app/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]