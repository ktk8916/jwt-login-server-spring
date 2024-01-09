FROM openjdk:17
COPY build/libs/*T.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/app.jar"]