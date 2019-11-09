package io.realworld.conduit.user.infrastructure.api

import io.realworld.conduit.test.WebServerTest
import io.realworld.conduit.test.expectJsonResponse
import io.realworld.conduit.test.jsonRequest
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
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
            spec(jsonRequest())
            body(body)
        } When {
            post("/api/users")
        } Then {
            statusCode(201)
            spec(expectJsonResponse())
        }
    }

}
