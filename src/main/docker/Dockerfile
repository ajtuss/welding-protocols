FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD welding-protocols.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","/app.jar"]