package io.realworld.conduit.profile.infrastructure.api

import io.realworld.conduit.test.WebServerTest
import io.realworld.conduit.test.createUser
import io.realworld.conduit.test.withDefaultUser
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@WebServerTest
class FollowProfileHandlerIntegrationTest {

    @Test
    fun `following an existing user works`() {
        val user = withDefaultUser()

        val userToFollow = createUser("pepe", "pepe@pepe.com", "pepe")

        Given {
            header("Authorization", "Token ${user.token}")
        } When {
            post("/api/profiles/${userToFollow.username}/follow")
        } Then {
            statusCode(200)
        }
    }
}
