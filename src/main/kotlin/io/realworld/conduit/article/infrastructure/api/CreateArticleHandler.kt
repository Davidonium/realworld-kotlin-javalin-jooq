package io.realworld.conduit.article.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.article.application.ArticleResponseBody
import io.realworld.conduit.article.application.CreateArticleRequest
import io.realworld.conduit.article.application.CreateArticleRequestBody
import io.realworld.conduit.article.application.CreateArticleService
import io.realworld.conduit.user.infrastructure.api.requireCurrentUserId

class CreateArticleHandler(private val createArticleService: CreateArticleService) {
    fun handle(ctx: Context) {
        val request = CreateArticleRequest(
            currentUserId = ctx.requireCurrentUserId(),
            article = ctx.bodyValidator<CreateArticleRequestBody>().get().article
        )
        val response = createArticleService.execute(request)
        ctx.json(ArticleResponseBody(response))
    }
}
