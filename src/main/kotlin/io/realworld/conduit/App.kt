package io.realworld.conduit

import io.javalin.Javalin
import io.realworld.conduit.article.infrastructure.injection.articleModule
import io.realworld.conduit.profile.infrastructure.injection.profileModule
import io.realworld.conduit.shared.infrastructure.injection.mainModule
import io.realworld.conduit.user.infrastructure.injection.userModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import javax.sql.DataSource

fun main() {
    App().start()
}

class App(
    private val properties: Map<String, Any> = mapOf(),
    private val initializeDatabase: Boolean = false
) {

    private val container = startKoin {
        fileProperties()
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

    init {
        System.setProperty("user.timezone", "UTC")
    }

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
