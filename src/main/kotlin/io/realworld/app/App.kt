package io.realworld.app

import io.javalin.Javalin
import io.realworld.app.shared.infrastructure.injection.mainModule
import org.koin.core.context.startKoin

fun main() {
    val app = startKoin {
        modules(listOf(mainModule))
    }
    app.koin.get<Javalin>().start()
}
