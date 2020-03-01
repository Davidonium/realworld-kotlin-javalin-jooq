package io.realworld.conduit.shared.infrastructure.injection

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zaxxer.hikari.HikariDataSource
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import io.realworld.conduit.shared.domain.SlugGenerator
import io.realworld.conduit.shared.infrastructure.api.ExceptionMapper
import io.realworld.conduit.shared.infrastructure.router.Router
import io.realworld.conduit.shared.infrastructure.slug.SlugifySlugGenerator
import javax.sql.DataSource
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.koin.dsl.module

val mainModule = module {
    single<DataSource> {
        HikariDataSource().apply {
            jdbcUrl = getProperty("DB_URL")
            username = getProperty("DB_USER")
            password = getProperty("DB_PASSWORD")
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
    single<Javalin> {
        JavalinJackson.configure(get())
        Javalin.create { config ->
            config.showJavalinBanner = false
            config.enableCorsForAllOrigins()
            config.contextPath = "/api"
        }.also {
            get<Router>().setupRoutes(it)
            get<ExceptionMapper>().map(it)

            it.server()?.serverPort = getProperty("APP_PORT")
        }
    }
    single {
        Router(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    single { ExceptionMapper() }
    single<SlugGenerator> { SlugifySlugGenerator() }
}
