package io.realworld.app.shared.infrastructure.injection

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zaxxer.hikari.HikariDataSource
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import io.realworld.app.shared.infrastructure.api.ArticleListHandler
import io.realworld.app.shared.infrastructure.router.Router
import javax.sql.DataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.koin.dsl.module

val mainModule = module {
    single<DataSource> {
        HikariDataSource().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5444/realworld"
            username = "rw"
            password = "rw"
            driverClassName = "org.postgresql.Driver"
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        }
    }
    single {
        Jdbi.create(get<DataSource>())
            .installPlugin(KotlinPlugin())
            .installPlugin(PostgresPlugin())
    }
    single {
        jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }
    single {
        JavalinJackson.configure(get())
        val app = Javalin.create { config ->
            config.showJavalinBanner = false
            config.enableCorsForAllOrigins()
        }

        get<Router>().setupRoutes(app)
        app
    }
    single { ArticleListHandler(get()) }
    single { Router(get()) }
}
