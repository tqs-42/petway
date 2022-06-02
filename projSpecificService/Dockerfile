FROM openjdk:11
COPY ./target/app-0.0.1-SNAPSHOT.jar projSpecificService.jar

EXPOSE 8000

CMD ["java", "-jar", "projSpecificService.jar"]