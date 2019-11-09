package io.realworld.conduit.test

import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.specification.RequestSpecification
import io.restassured.specification.ResponseSpecification


fun jsonRequest(): RequestSpecification {
    return RequestSpecBuilder()
        .setContentType("application/json")
        .setAccept("application/json")
        .build()
}

fun expectJsonResponse(): ResponseSpecification {
    return ResponseSpecBuilder()
        .expectContentType("application/json")
        .build()
}
