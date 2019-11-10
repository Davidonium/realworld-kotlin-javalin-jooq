package io.realworld.conduit.test

import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
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

fun withDefaultUser(): TestUser {
    return createUser("david", "test.email@gmail.com", "123")
}

fun createUser(username: String, email: String, password: String): TestUser {
    val body = object {
        val user = object {
            val username = username
            val email = email
            val password = password
        }
    }

    val userResponseBody = Given {
        spec(jsonRequest())
        body(body)
    } When {
        post("/api/users")
    } Then {
        statusCode(201)
        spec(expectJsonResponse())
    } Extract {
        `as`(TestUserResponse::class.java)
    }

    return userResponseBody.user
}

data class TestUserResponse(
    val user: TestUser
)

data class TestUser(
    val email: String,
    val token: String,
    val username: String,
    val bio: String?,
    val image: String?
)
