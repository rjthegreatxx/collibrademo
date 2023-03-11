FROM openjdk:17-jdk
COPY build/libs/collibrademo-0.0.1-SNAPSHOT.jar collibrademo-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "collibrademo-0.0.1-SNAPSHOT.jar"]