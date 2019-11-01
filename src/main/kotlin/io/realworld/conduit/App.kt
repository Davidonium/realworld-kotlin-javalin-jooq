package io.realworld.conduit

import io.javalin.Javalin
import io.realworld.conduit.article.infrastructure.injection.articleModule
import io.realworld.conduit.shared.infrastructure.injection.mainModule
import io.realworld.conduit.user.infrastructure.injection.userModule
import org.koin.core.context.startKoin

fun main() {
    System.setProperty("user.timezone", "UTC")

    val app = startKoin {
        fileProperties()
        modules(listOf(mainModule, articleModule, userModule))
    }
    app.koin.get<Javalin>().start()
}
