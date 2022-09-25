# TedTalks Spring Boot App

## Requirements

For building and running the application you need:

- MySQL
- JDK 11
- Maven

## Running the application locally

edit the application properties file to connect the mysql database

```file
application.properties
```

then run

```shell
mvn spring-boot:run
```

then navigate to http://localhost:8080/

you will find a full documentation of the api through the link https://documenter.getpostman.com/view/7033532/2s83RwjvSg

you can import the postman project [TedTalks.postman_collection.json](helpers/TedTalks.postman_collection.json)

## Upload the sample data

use the following end point to upload the sample data

```
/api/v1/talks/bulk
```

the sample data is converted to json [talksData.json](helpers/talksData.json)

## Deploying the application to Docker

- Run the following commands

```shell
./mvnw spring-boot:build-image
docker run -p 8080:8080 -t tedtalks:0.0.1-SNAPSHOT
```