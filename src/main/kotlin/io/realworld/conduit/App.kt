package io.realworld.conduit

import io.javalin.Javalin
import io.realworld.conduit.article.infrastructure.injection.articleModule
import io.realworld.conduit.profile.infrastructure.injection.profileModule
import io.realworld.conduit.shared.infrastructure.injection.mainModule
import io.realworld.conduit.user.infrastructure.injection.userModule
import javax.sql.DataSource
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.error.NoPropertyFileFoundException

fun main() {
    App().start()
}

class App(
    private val properties: Map<String, Any> = mapOf(),
    private val initializeDatabase: Boolean = false
) {
    init {
        System.setProperty("user.timezone", "UTC")
    }

    private val container = startKoin {
        try {
            fileProperties("/application.properties")
        } catch (e: NoPropertyFileFoundException) {
            // properties can be loaded from environment or hard-coded
        }
        environmentProperties()
        properties(properties)
        modules(listOf(
            mainModule,
            articleModule,
            userModule,
            profileModule
        ))
    }.koin

    private val app = container.get<Javalin>()

    fun start() {
        if (initializeDatabase) {
            val initSql = Thread.currentThread()
                .contextClassLoader
                .getResourceAsStream("db/init.sql")
                ?.reader().use { it?.readText() }
            container.get<DataSource>().connection.use {
                it.prepareStatement(initSql).execute()
            }
        }

        app.start()
    }

    fun stop() {
        app.stop()
        stopKoin()
    }

    fun port() = app.port()
}
