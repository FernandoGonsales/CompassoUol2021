![Logo Compasso](https://upload.wikimedia.org/wikipedia/commons/f/f3/LogoCompasso-positivo.png)

# Desafio Catálogo de Produtos API Rest - Java Spring - Compasso UOL

#### Autor: Fernando Franchi Gonsales | [LinkedIn](https://www.linkedin.com/in/fernando-fgonsales/)

## Requisitos do Projeto:

Confira Aqui: [COMPASSO-README.md](https://bitbucket.org/RecrutamentoDesafios/desafio-java-springboot/src/master/)

## Stacks Utilizadas:

:ballot_box_with_check: Java 11

:ballot_box_with_check: Spring Boot

:ballot_box_with_check: Spring Data JPA

:ballot_box_with_check: Swagger

:ballot_box_with_check: Maven

:ballot_box_with_check: Lombok

:ballot_box_with_check: EntityManager (Dinamic Query)

:ballot_box_with_check: Banco de dados H2 (in-memory)

:ballot_box_with_check: Modelo arquitetural REST

:ballot_box_with_check: Docker/Docker-Compose

:ballot_box_with_check: Testes de Integração/Mock

:ballot_box_with_check: Alguns Principios de SOLID/Design Pattern

## Rodar o projeto pelo Maven (porta 9999):
(Skip Tests)
Entre na pasta raiz do projeto (productapi) e execute o comando:

```shell
mvn clean package spring-boot:run -Dmaven.test.skip=true
```

## Rodar os Testes:

Entre na pasta raiz do projeto e execute o comando:

```shell
mvn clean test
```

## Acessar Swagger:

após iniciar o projeto entre no link:

http://localhost:9999/swagger-ui.html

## Acessar banco H2:

http://localhost:9999/h2

Login: sa

Password: (vazio)

## Rodar o projeto pelo Docker:

Requisitos: `docker` e `docker-compose`.

Com docker rodando, execute o comando na pasta raiz do projeto:

```shell
mvn clean package -DskipTests && docker-compose up -d --build
```

## Finalizar container e remover volume/imagem/containers:

```shell
docker-compose down -v --remove-orphans 
```

# CompassoUol2021
Prova técnica da Compasso UOL, utilizando Java, Spring, API Rest, Swagger, Testes.
