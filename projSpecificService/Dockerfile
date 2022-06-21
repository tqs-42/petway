FROM maven:3.8.1-adoptopenjdk-11

WORKDIR /specificService

COPY . /specificService/

RUN mvn -DskipTests clean package

ENTRYPOINT ["java","-jar","/specificService/target/app-0.0.1-SNAPSHOT.jar"]