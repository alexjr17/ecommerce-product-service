# Dockerfile
FROM gradle:7.6.1-jdk17 as builder
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew .
COPY gradlew.bat .
COPY src ./src
RUN chmod +x ./gradlew
RUN ./gradlew build -x test
#java -Duser.timezone="America/Bogota" -jar app.jar


FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]