FROM adoptopenjdk/openjdk11:jre-11.0.8_10-debianslim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} project-brijframework-authorization-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","project-brijframework-authorization-0.0.1-SNAPSHOT.jar"]