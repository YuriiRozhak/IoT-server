# 1️⃣ Build React UI
FROM node:18 AS frontend-build
WORKDIR /app
COPY frontend /app
RUN npm install && npm run build

# 2️⃣ Build Spring Boot Backend
FROM maven:3.8.4-openjdk-17 AS backend-build
ARG API_KEY
ARG CLIENT_ID
ARG BROKER_URL
WORKDIR /app
COPY --from=frontend-build /app/build /app/src/main/resources/static/
COPY . /app
RUN sed -i "s/API_KEY/${API_KEY}/g" /app/src/main/resources/application.properties
RUN sed -i "s#BROKER_URL#${BROKER_URL}#g" /app/src/main/resources/application.properties
RUN sed -i "s/CLIENT_ID/${CLIENT_ID}/g" /app/src/main/resources/application.properties
RUN mvn package -DskipTests

# 3️⃣ Run the Application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the application JAR
COPY --from=backend-build /app/target/*.jar /app/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]