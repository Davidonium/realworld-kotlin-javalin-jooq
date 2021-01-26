# ![RealWorld Example App](logo.png)

> ### Kotlin + Javalin + Jooq codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.


### [Demo](https://github.com/gothinkster/realworld) | [RealWorld](https://github.com/gothinkster/realworld)


This codebase was created to demonstrate a fully fledged fullstack application built with **Kotlin + http4k + Jooq** including CRUD operations, authentication, routing, pagination, and more.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.


# How it works

Hexagonal architecture realworld implementation

# Getting started

```bash
$ docker-compose up -d
$ cp src/main/resources/application-local.conf.dist src/main/resources/application-local.conf
$ ./gradlew jooq
$ ./gradlew run
```
