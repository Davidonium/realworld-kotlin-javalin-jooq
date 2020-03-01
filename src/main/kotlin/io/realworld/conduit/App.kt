package io.realworld.conduit

import io.javalin.Javalin
import io.realworld.conduit.article.infrastructure.injection.articleModule
import io.realworld.conduit.profile.infrastructure.injection.profileModule
import io.realworld.conduit.shared.infrastructure.injection.mainModule
import io.realworld.conduit.user.infrastructure.injection.userModule
import org.koin.core.context.stopKoin
import javax.sql.DataSource
import org.koin.core.error.NoPropertyFileFoundException
import org.koin.dsl.koinApplication
import java.time.ZoneId
import java.util.TimeZone

fun main() {
    App().start()
}

class App(
    private val properties: Map<String, Any> = mapOf(),
    private val initializeDatabase: Boolean = false
) {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    private val app = koinApplication {
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
    }

    private val container = app.koin

    private val javalin = container.get<Javalin>()

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

        javalin.start()
    }

    fun stop() {
        javalin.stop()
        app.close()
    }

    fun port() = javalin.port()
}
