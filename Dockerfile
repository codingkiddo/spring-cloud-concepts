FROM eclipse-temurin:17
WORKDIR workspace
ARG JAR_FILE=target/spring-cloud-concepts-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} spring-cloud-concepts.jar
ENTRYPOINT [ "java", "-jar", "spring-cloud-concepts.jar" ]