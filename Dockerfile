FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY pharmacy.csv ./
ENTRYPOINT ["java","-jar","/app.jar"]