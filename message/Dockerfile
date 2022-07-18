FROM openjdk:11-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} getMessageApplication.jar
ENTRYPOINT ["java","-jar","/getMessageApplication.jar"]
EXPOSE 8080