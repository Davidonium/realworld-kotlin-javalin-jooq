package io.realworld.conduit

import io.javalin.Javalin
import io.realworld.conduit.article.infrastructure.injection.articleModule
import io.realworld.conduit.shared.infrastructure.injection.mainModule
import org.koin.core.context.startKoin

fun main() {
    val app = startKoin {
        modules(listOf(mainModule, articleModule))
    }
    app.koin.get<Javalin>().start()
}
