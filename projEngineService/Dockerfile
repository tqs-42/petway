FROM maven:3.8.1-adoptopenjdk-11

WORKDIR /engineService

COPY . /engineService/

RUN mvn -DskipTests clean package

ENTRYPOINT ["java","-jar","/engineService/target/app-0.0.1-SNAPSHOT.jar"]