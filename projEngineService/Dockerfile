FROM maven:3.8.2-jdk-11

WORKDIR /projEngineService

COPY . /projEngineService

RUN mvn clean install

CMD mvn spring-boot:run