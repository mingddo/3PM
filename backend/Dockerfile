FROM openjdk:11-jdk-alpine
VOLUME /tmp
ADD ./build/libs/sns-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java", "-jar", "/app.jar"]
