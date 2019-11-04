package io.realworld.conduit.profile.infrastructure.api

import io.realworld.conduit.test.WebServerTest
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.Test

@WebServerTest
class ProfileHandlerIntegrationTest {

    @Test
    fun `get a profile that exists`() {
        val response = When {
            get("/api/profiles/Superman")
        }

        println(response.body.prettyPrint())
    }
}
