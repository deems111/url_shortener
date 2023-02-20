FROM maven:latest

RUN mkdir -p /docker
WORKDIR /docker

ADD pom.xml /docker

RUN mvn clean -B
RUN mvn dependency:resolve-plugins -B
RUN mvn dependency:resolve -B

ADD ./ /docker/

RUN mvn install -B -DskipTests


FROM openjdk:17
LABEL author="Me"
RUN mkdir -p /docker
WORKDIR /docker
COPY --from=0 /docker/target/shortener-1.0-SNAPSHOT.jar /docker/

EXPOSE 8080
CMD ["java", "-jar", "/docker/shortener-1.0-SNAPSHOT.jar"]

