# redis


This sample application demonstrates a simple redis services.

The redis service offers the following REST endpoints:

Swagger UI : http://localhost:8080/swagger-ui/index.html

Api doc: http://localhost:8080/v2/api-docs?group=api


For simplicity, no authentication is implemented

## Getting Started



These instructions allow you to run a copy of the running project on your local machine for development and testing purposes.
### Prerequisites

- Java 11

### Installing

**Note:** Before compilation, you might configure your IDE with Lombok plugin

Compilation

```
./gradlew build
```

Running locally

```
./gradlew bootRun
```


## Running the test cases

Run the test cases execute

```
./gradlew test
```


## Build With

- SpringBoot
- Spring Framework (Core, MVC)
- Mockk
- JUnit 5 Jupiter
- Mockito
- Gradle 7.1.x

## Architecture
To determine the architecture of this service I rely on this PDF  https://dsf.berkeley.edu/papers/fntdb07-architecture.pdf
where we have the following layers
- web : for access via api rest
- templates: access to a template where there are the methods to access the storage
- storage: access to the collections where the information is stored

## Author

- Gabriel Ortiz - (https://github.com/gaortiz1)
