package io.realworld.app.shared.infrastructure.router

import io.javalin.Javalin
import io.realworld.app.shared.infrastructure.api.ArticleListHandler


class Router(private val articleListHandler: ArticleListHandler) {

    fun setupRoutes(app: Javalin) {
        app.get("/articles", articleListHandler::handle)
    }
}