############ clean build & run #############
FROM openjdk:8-jdk-alpine AS build
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew build

FROM openjdk:8-jdk-alpine
COPY --from=build app/build/libs/Bill-0.0.1-SNAPSHOT.jar bill.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bill.jar"]


########### As simple as possible #############
#FROM openjdk:8-jdk-alpine
#USER root
#COPY . .
#RUN ./gradlew clean build
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "/build/libs/Bill-0.0.1-SNAPSHOT.jar"]


############ clean build & run #############
#FROM openjdk:8-jdk-alpine AS TEMP_BUILD_IMAGE
#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
#COPY build.gradle settings.gradle gradlew $APP_HOME
#COPY gradle $APP_HOME/gradle
#RUN  ./gradlew build --chmod +x|| return 0
#COPY . .
#RUN  ./gradlew build --chmod +x
#
#FROM openjdk:8-jdk-alpine
#ENV APP_HOME=/usr/app/
#WORKDIR $APP_HOME
#COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/Bill-0.0.1-SNAPSHOT.jar .
#EXPOSE 8080
#CMD ["java","-jar", "Bill-0.0.1-SNAPSHOT.jar"]