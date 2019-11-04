package io.realworld.conduit.test

import io.javalin.Javalin
import io.realworld.conduit.App
import io.restassured.RestAssured
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.testcontainers.containers.PostgreSQLContainer

class WebServerExtension : BeforeAllCallback, AfterAllCallback {
    private lateinit var app: App

    override fun beforeAll(context: ExtensionContext) {
        val databaseContainer = PostgreSQLContainer<Nothing>()

        databaseContainer.start()
        val properties = mapOf(
            "DB_URL" to databaseContainer.jdbcUrl,
            "DB_USER" to databaseContainer.username,
            "DB_PASSWORD" to databaseContainer.password,
            "APP_PORT" to 0
        )

        app = App(
            properties = properties,
            initializeDatabase = true
        )
        app.start()
        RestAssured.port = app.port()
    }

    override fun afterAll(context: ExtensionContext) {
        app.stop()
    }
}
