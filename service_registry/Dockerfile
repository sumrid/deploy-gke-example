FROM openjdk:8-jdk-alpine

COPY /build/libs/service_registry-1.0.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/app.jar"]