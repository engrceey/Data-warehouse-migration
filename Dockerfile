FROM openjdk:17-jdk-slim-buster
WORKDIR /api
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /myjar/clustereddatawarehouse.jar
EXPOSE 8002

CMD ["java", "-jar", "/myjar/clustereddatawarehouse.jar"]

