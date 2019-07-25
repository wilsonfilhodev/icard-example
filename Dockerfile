FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]