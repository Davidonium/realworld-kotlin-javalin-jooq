package io.realworld.conduit.article.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.article.application.ViewRecentArticlesRequest
import io.realworld.conduit.article.application.ViewRecentArticlesService
import io.realworld.conduit.user.infrastructure.api.currentUserId

class RecentArticlesHandler(private val recentArticlesService: ViewRecentArticlesService) {
    fun handle(ctx: Context) {

        val request = ViewRecentArticlesRequest(
            currentUserId = ctx.currentUserId(),
            tag = ctx.queryParam("tag"),
            author = ctx.queryParam("author"),
            favorited = ctx.queryParam("favorited"),
            limit = ctx.queryParam<Int>("limit").getOrNull() ?: 20,
            offset = ctx.queryParam<Int>("offset").getOrNull() ?: 0
        )
        ctx.json(recentArticlesService.execute(request))
    }
}
