FROM openjdk:8-jdk-alpine
USER root
COPY . .
RUN ./gradlew clean build
EXPOSE 8080
CMD ["java", "-jar", "/build/libs/Report-0.0.1-SNAPSHOT.jar"]