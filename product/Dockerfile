################# build docker image ###################
FROM openjdk:8-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package

FROM openjdk:8-jdk-alpine
COPY --from=build /app/target/product_service.jar product.jar
EXPOSE 8080
CMD ["java", "-jar", "product.jar"]
