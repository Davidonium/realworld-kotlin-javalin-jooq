package io.realworld.conduit.shared.infrastructure.injection

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariDataSource
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import io.realworld.conduit.shared.domain.SlugGenerator
import io.realworld.conduit.shared.domain.UnitOfWork
import io.realworld.conduit.shared.infrastructure.api.ExceptionMapper
import io.realworld.conduit.shared.infrastructure.persistence.spring.SpringJooqTransactionProvider
import io.realworld.conduit.shared.infrastructure.persistence.spring.SpringUnitOfWork
import io.realworld.conduit.shared.infrastructure.router.Router
import io.realworld.conduit.shared.infrastructure.slug.SlugifySlugGenerator
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.impl.DefaultConfiguration
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import javax.sql.DataSource

val mainModule = module {
    single<DataSource> {
        HikariDataSource().apply {
            jdbcUrl = get<Config>().getString("db.url")
            username = get<Config>().getString("db.user")
            password = get<Config>().getString("db.password")
            driverClassName = "org.postgresql.Driver"
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        }
    }
    single<DataSource>(named("transactionDataSource")) { TransactionAwareDataSourceProxy(get<DataSource>()) }
    single<PlatformTransactionManager> { DataSourceTransactionManager(get<DataSource>()) }
    single {
        val config = DefaultConfiguration()
            .set(SQLDialect.POSTGRES)
            .set(SpringJooqTransactionProvider(get()))
            .set(get<DataSource>(named("transactionDataSource")))

        DSL.using(config)
    }
    single<UnitOfWork> { SpringUnitOfWork(TransactionTemplate(get())) }
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

            it.server()?.serverPort = get<Config>().getInt("app.port")
        }
    }
    single { ExceptionMapper() }
    single<SlugGenerator> { SlugifySlugGenerator() }
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
            get(),
            get()
        )
    }
    single<Config> {
        val default = ConfigFactory.parseResources("application.conf")

        ConfigFactory
            .parseResources("application-local.conf")
            .withFallback(default)
    }
}
