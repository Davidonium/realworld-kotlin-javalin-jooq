package io.realworld.conduit.shared.infrastructure.router

import io.javalin.Javalin
import io.realworld.conduit.article.infrastructure.api.ArticleListHandler

class Router(
    private val articleListHandler: ArticleListHandler
) {

    fun setupRoutes(app: Javalin) {
        app.get("/articles/:slug", articleListHandler::handle)
    }
}
