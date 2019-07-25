<p align="center">
  <a href="https://spring.io/">
    <img src="https://spring.io/img/homepage/icon-spring-boot.svg" alt="Logo" width=72 height=72>
  </a>

  <h3 align="center">API Rest iCARD</h3>

  <p align="center">
    This is a sample Java / Maven / Spring Boot (version 2.1.6) application that can be used for create credit cards and authorize purchases.
  </p>
</p>

## Contents

- [Overview](#overview)
- [Quick start](#quick-start)
- [Docker](#docker)
- [What's included](#whats-included)
- [Documentation](#documentation)
- [Authors](#authors)
- [License](#license)


## Overview


The API is just a credit card creation and transaction authorization REST service. It uses an MongoDB database to store the data. You can run this application in a Docker container.

This project was developed following the MVC model. By adopting this architectural model the maintenance and evolution of the application becomes easier. When applying this model correctly the developer intuitively brings to his application some design patterns.

We adopted as Spring Boot framework that also already follows the MVC model, thus facilitating the development of api.

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the java -jar command.

Here is what this little application demonstrates:

- Full integration with the latest Spring Framework: inversion of control, dependency injection, etc.
- Writing a RESTful Service Using HTTP Annotation and Verbs
- Mapping Application Exceptions to Correct HTTP Response with Exception Details in Body
- Automatic CRUD Functionality Using Standard Spring Mongo Repository
- Demonstrates MockMVC test framework and Junit 
- Documentation using Swagger2 annotations
- Web address API health monitoring http://localhost:8080/actuator/health


## Quick start

**Warning**

> Verify that you are running at least JDK 1.8+ and Maven 3.3+ by running java -version and mvn -version in a terminal/console window. Older versions produce errors, but newer versions are fine.

> This api uses MongoDB database, so it is necessary that you have this database available. You can changing the connection data in file `application.properties`.

1. Download or clone project

2. Go to project folder and execute command.
 ```bash
 mvn clean spring-boot:run
 ```
 
Once the application runs you should see something like this
```bash
2019-07-25 01:33:07.261  INFO 1492 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2019-07-25 01:33:07.263  INFO 1492 --- [  restartedMain] com.example.icard.IcardApplication       : Started IcardApplication in 10.716 seconds (JVM running for11.818)

```

## Docker

*Warning**

> To run this application in a docker container you must have JDK 8+, Maven, Docker and Docker-compose.

1. Download or clone project

2. Go to project folder and execute command.
 ```bash
 $ mvn package
 ```
 ```bash
 $ docker build -t icard .
 ```
 ```bash
 $ docker-compose up
 ```

## What's included

* Create new card

```
  POST /api/cards
  Accept: application/json
  Content-Type: application/json
  
  {
    "nome": "Steven N. Lester",
    "saldo": 2300.00
  }
```

```
  Example return data.
  
  RESPONSE: HTTP 201 (Created)
  
 {
    "cvv": "016",
    "nome": "Steven N. Lester",
    "numero": "5555556716962161",
    "validade": "07/21",
    "senha": "7807",
    "saldo": 2300.00
}
```

**Notes**
> All attributes are required.

* Sale Authorization

```
  POST /api/cards/authorization
  Accept: application/json
  Content-Type: application/json
  
  {
    "cartao": "5555556716962161",
    "validade": "07/21",
    "cvv": "016",
    "estabelecimento": "Elsie Muller LTDA",
    "valor": 149.19,
    "senha": "7807"
  }
```

```
  Example return data (SUCCESS).
  
  RESPONSE: HTTP 200 (OK)
  
 {
    "codigo": "00",
    "saldo": 1300.00
}
```

```
  Example return data (ERROR).
  
  RESPONSE: HTTP 400 (Bad Request)
  
 {
    "codigo": "105",
    "erros": [
          "Transação não autorizada. Saldo insuficiente."
        ]
 }
```

**Notes**
> All attributes are required.


## Documentation

This project is documented with Swagger 2 API docs. Run the server and browse to `localhost:8080/swagger-ui.html`


## Authors

* **Wilson Filho**  - [Linkedin](https://www.linkedin.com/in/wilson-filho-4424b5bb)

## License

This project is licensed under the MIT License.
