FROM openjdk:8-jdk-alpine
USER root
COPY /build/libs/Report-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]