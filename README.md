<p align="center">
  <a href="https://spring.io/">
    <img src="https://spring.io/img/homepage/icon-spring-boot.svg" alt="Logo" width=72 height=72>
  </a>

  <h3 align="center">API Rest iCARD - Spring Boot + MongoDB</h3>

  <p align="center">
    This is a sample Java / Maven / Spring Boot (version 2.1.6) application that can be used for create credit cards and authorize purchases.
  </p>
</p>

## Contents

- [Overview](#overview)
- [Quick start](#quick-start)
- [What's included](#whats-included)
- [Documentation](#documentation)
- [Authors](#authors)
- [License](#license)


## Overview


The API is just a credit card creation and transaction authorization REST service. It uses an cloud database (MLAB) to store the data.

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


## Quick start

**Warning**

> Verify that you are running at least JDK 1.8+ and Maven 3.3+ by running java -version and mvn -version in a terminal/console window. Older versions produce errors, but newer versions are fine.

> This api uses cloud database (MLab), so it is necessary is connected to the internet to utilize the features of this project. 
You can point to a local database by changing the connection data in the `application.properties` file.

1. Download or clone project

2. Go to project folder and execute command.
 ```bash
 mvn clean spring-boot:run
 ```
 

## What's included

* Create new card

**POST** http://localhost:8080/api/cards
```
  Example data send in body (JSON)
  
  {
    "nome": "Steven N. Lester",
    "saldo": 2300.00
  }
```

```
  Example return data.
  
 {
    "nome": "1234",
    "numero": "5555551234567890",
    "cvv": "071",
    "validade": "07/21",
    "senha": "1234",
    "saldo": 2300.00
}
```

**Notes**
> All attributes are required.

* Sale Authorization

**POST** http://localhost:8080/api/cards/authorization
```
  Example data send in body (JSON)
  
  {
    "cartao": "5555558908835049",
    "validade": "07/21",
    "cvv": "242",
    "estabelecimento": "Elsie Muller LTDA",
    "valor": 149.19,
    "senha": "6442"
  }
```

```
  Example return data (SUCCESS).
  
 {
    "codigo": "00",
    "saldo": 1300.00
}
```

```
  Example return data (ERROR).
  
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
