package io.realworld.conduit.shared.infrastructure.injection

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zaxxer.hikari.HikariDataSource
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import io.realworld.conduit.shared.infrastructure.router.Router
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.koin.dsl.module
import javax.sql.DataSource

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
        DSL.using(get<DataSource>(), SQLDialect.POSTGRES)
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
            config.contextPath = "/api"
        }

        get<Router>().setupRoutes(app)
        app
    }
    single { Router(get()) }
}
