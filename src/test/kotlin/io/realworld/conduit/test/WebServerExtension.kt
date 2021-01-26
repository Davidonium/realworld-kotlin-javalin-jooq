package io.realworld.conduit.test

import io.realworld.conduit.App
import io.restassured.RestAssured
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Namespace
import org.junit.jupiter.api.extension.ExtensionContext.Store
import org.testcontainers.containers.PostgreSQLContainer

class WebServerExtension : BeforeAllCallback {
    override fun beforeAll(context: ExtensionContext) {
        val store = rootStore(context)
        store.getOrComputeIfAbsent("app") {
            val databaseContainer = KPostgreSQLContainer("postgres:11.5")

            databaseContainer.start()
            val properties = mapOf(
                "DB_URL" to databaseContainer.jdbcUrl,
                "DB_USER" to databaseContainer.username,
                "DB_PASSWORD" to databaseContainer.password,
                "APP_PORT" to 0
            )

            val app = App(
                properties = properties,
                initializeDatabase = true
            )
            app.start()
            RestAssured.port = app.port()

            app
        }
    }

    private fun rootStore(context: ExtensionContext): Store {
        return context.root.getStore(Namespace.create(WebServerTest::class.java))
    }

    class KPostgreSQLContainer(imageName: String) : PostgreSQLContainer<KPostgreSQLContainer>(imageName)
}
