package io.realworld.conduit.user.infrastructure.api

import io.realworld.conduit.test.WebServerTest
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.specification.RequestSpecification
import io.restassured.specification.ResponseSpecification
import org.junit.jupiter.api.Test

@WebServerTest
class UserSignupHandlerIntegrationTest {

    @Test
    fun `a user can sign up successfully`() {
        val body = """{
  "user": {
    "username": "david",
    "email": "david.hernando91@gmail.com",
    "password": "123"
  }
}"""
        Given {
            jsonRequest()
            body(body)
        } When {
            post("/api/users")
        } Then {
            statusCode(201)
            expectJsonResponse()
        }
    }

    private fun jsonRequest(): RequestSpecification {
        return RequestSpecBuilder()
            .setContentType("application/json")
            .build()
    }

    private fun expectJsonResponse(): ResponseSpecification {
        return ResponseSpecBuilder()
            .expectContentType("application/json; charset=utf-8")
            .build()
    }
}
