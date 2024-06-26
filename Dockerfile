FROM openjdk:11

ARG JAR_FILE=goms-infrastructure/build/libs/goms-infrastructure-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]