FROM openjdk:8-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build

FROM openjdk:8-jdk-alpine
COPY --from=build /app/build/libs/apigateway-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "apigateway-0.0.1-SNAPSHOT.jar"]