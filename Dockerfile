FROM openjdk:11
ARG JAR_FILE=build/libs/app.jar
COPY ${JAR_FILE} ./app.jar
COPY pharmacy.csv ./
ENV TZ=Asia/Seoul
ENTRYPOINT ["java","-jar","./app.jar"]
