package io.realworld.app.shared.infrastructure.api

import io.javalin.http.Context
import io.realworld.app.article.domain.Article
import io.realworld.app.shared.infrastructure.persistence.handled
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.mapTo

class ArticleListHandler(private val db: Jdbi) {
    fun handle(ctx: Context) {
        val articles = db.handled { h ->
            val sql = """select id,
                                slug,
                                title,
                                description,
                                body,
                                created_at,
                                updated_at
                           from articles"""
            h.createQuery(sql).mapTo<Article>().list()
        }
        ctx.json(mapOf("articles" to articles))
    }
}
