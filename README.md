## _Url Shortener Application_

✨REST application using Spring Boot ver 2.X.X and relational database.
Using Spring AOP, Liquibase to generate schema, any mapper and internationalization.

### Features
- Add User
- Get User
- Short Url
- Get original (full Url) by Short Url
- Get all Urls by User
- Get all Urls by time period

> For shorting Url - the CRC32 Algorithm is used, learn more - https://commandlinefanatic.com/cgi-bin/showarticle.cgi?article=art008

### Tech

- [Java 17]
- [Spring Boot 2.7.5] - including Spring Data, Spring AOP, Spring Validation

Databases
- [PostgreSQL]
- [MySQL]
- [H2] - test only

Other
- [Maven] 
- [Liquibase]
- [Swagger UI] - ver.3 - Springdoc
- [MapStruct]
- [Lombok]

### Run
Application requires [Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) v17+ to run.

##### 1) Check datasource settings
Update datasource settings in SRC/MAIN/RESOURCES/APPLICATION.YML. By default - using PostgreSQl in app and in-memory H2 in tests.

##### 2) Install the dependencies. Building for source
```sh

mvn clean package
```
##### 3) Run the application
```sh

mvn spring-boot:run
```

##### Use swagger to call API
Open [Swagger](http://localhost:8080/swagger-ui/index.html).
![Swagger screenshot](https://i.ibb.co/2W4TZGR/Capture.png "Swagger screenshot")


### Docker

##### 1) Check ports settings in .dockerfile and docker-compose.yml.

##### 2) Remove one service postgresdb / mysqldb from docker-compose.yml.

##### 3) Run

```sh

docker compose run 
```
