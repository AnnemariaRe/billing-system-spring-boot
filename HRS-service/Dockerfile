FROM openjdk:17

ARG JAR_FILE=target/*.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

CMD ["java", "-jar", "app.jar"]
