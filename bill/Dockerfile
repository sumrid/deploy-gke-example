############ clean build & run #############
FROM gradle:5.3.1-jdk8-alpine as build
WORKDIR /work
USER root
COPY . .
RUN gradle clean build

FROM openjdk:8-jdk-alpine
WORKDIR /deploy
COPY --from=build work/build/libs/Bill-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


########### As simple as possible #############
#FROM openjdk:8-jdk-alpine
#COPY /build/libs/Bill-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]


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