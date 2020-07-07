FROM openjdk:8-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build

FROM openjdk:8-jdk-alpine
COPY --from=build /app/build/libs/service_registry-1.0.jar app.jar
EXPOSE 8761
CMD ["java", "-jar", "app.jar"]