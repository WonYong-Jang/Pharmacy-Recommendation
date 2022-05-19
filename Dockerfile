FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
COPY pharmacy.csv ./
ENTRYPOINT ["java","-jar","/app.jar"]