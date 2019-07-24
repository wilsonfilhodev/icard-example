
<h3>API Rest iCARD</h3>

<p>API Restfull example using Spring Boot + MongoDB. In this project it is possible to create cards and authorize purchases.</p>

## Contents

- [Overview](#overview)
- [Quick start](#quick-start)
- [What's included](#whats-included)
- [Authors](#authors)
- [License](#license)


## Overview

This project was developed following the MVC model. By adopting this architectural model the maintenance and evolution of the application becomes easier. When applying this model correctly the developer intuitively brings to his application some design patterns.

We adopted as Spring Boot framework that also already follows the MVC model, thus facilitating the development of api.


## Quick start

**Warning**

> Verify that you are running at least JDK 1.8+ and Maven 3.3+ by running java -version and mvn -version in a terminal/console window. Older versions produce errors, but newer versions are fine.

> This api uses cloud database (MLab), so it is necessary is connected to the internet to utilize the features of this project.

1. Download or clone project

2. Go to project folder and execute command.
 ```bash
 mvn clean spring-boot:run
 ```
 

## What's included

* Create new card

**POST** http://localhost:8080/api/cards
```
  Example data send in body
  
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
  Example data send in body
  
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
  Example return data.
  
 {
    "codigo": "00",
    "saldo": 1300.00
}
```

**Notes**
> All attributes are required.


## Authors

* **Wilson Filho**  - [Linkedin](https://www.linkedin.com/in/wilson-filho-4424b5bb)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
