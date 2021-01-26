package io.realworld.conduit

import com.typesafe.config.Config
import io.javalin.Javalin
import io.realworld.conduit.article.infrastructure.injection.articleModule
import io.realworld.conduit.profile.infrastructure.injection.profileModule
import io.realworld.conduit.shared.infrastructure.injection.mainModule
import io.realworld.conduit.user.infrastructure.injection.userModule
import org.koin.dsl.koinApplication
import org.slf4j.LoggerFactory
import java.util.TimeZone
import javax.sql.DataSource

fun main() {
    App().start()
}

private val logger = LoggerFactory.getLogger(App::class.java)

enum class Environment {
    TEST,
    DEV
}

class App {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    }

    private val app = koinApplication {
        modules(
            listOf(
                mainModule,
                articleModule,
                userModule,
                profileModule
            )
        )
    }

    private val container = app.koin

    private val javalin = container.get<Javalin>()

    fun start() {
        if (container.get<Config>().getBoolean("db.initialize")) {
            logger.debug("Initializing database...")
            val initSql = Thread.currentThread()
                .contextClassLoader
                .getResourceAsStream("db/init.sql")
                ?.reader().use { it?.readText() }
            container.get<DataSource>().connection.use {
                it.prepareStatement(initSql).execute()
            }
            logger.debug("Database initialized successfully")
        }

        javalin.start()
    }

    fun stop() {
        javalin.stop()
        app.close()
    }

    fun port() = javalin.port()
}
