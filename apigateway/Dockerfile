FROM openjdk:8-jdk-alpine
USER root
COPY . .
RUN ./gradlew clean build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/build/libs/apigateway-0.0.1-SNAPSHOT.jar"]