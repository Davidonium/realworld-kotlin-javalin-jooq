package io.realworld.conduit.article.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.article.domain.ArticleRepository

class ArticleListHandler(private val articleRepository: ArticleRepository) {
    fun handle(ctx: Context) {
        val articles = articleRepository.all()
        ctx.json(mapOf("article" to articles))
    }
}
