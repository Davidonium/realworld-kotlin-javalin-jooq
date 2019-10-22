package io.realworld.app

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zaxxer.hikari.HikariDataSource
import io.javalin.Javalin
import io.javalin.plugin.json.JavalinJackson
import io.realworld.app.shared.infrastructure.injection.mainModule
import io.realworld.app.shared.infrastructure.persistence.handled
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.postgres.PostgresPlugin
import org.koin.core.context.startKoin
import java.text.SimpleDateFormat
import java.time.OffsetDateTime


fun main() {

    val app = startKoin {
        modules(listOf(mainModule))
    }

    app.koin.get<Javalin>().start()
}




data class User(
    val id: Int,
    val email: String,
    val password: String,
    val token: String,
    val bio: String,
    val image: String
)