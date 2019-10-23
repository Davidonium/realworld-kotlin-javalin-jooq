package io.realworld.conduit.article.infrastructure.persistence

import io.realworld.conduit.article.domain.Article
import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.generated.database.Tables.ARTICLES
import org.jooq.DSLContext


class JooqArticleRepository(private val ctx: DSLContext) : ArticleRepository {
    override fun bySlug(slug: String): Article? {
        return ctx.use { c ->
            c.select().from(ARTICLES).fetch {
                Article(
                    id = it[ARTICLES.ID],
                    slug = it[ARTICLES.SLUG],
                    title = it[ARTICLES.TITLE],
                    description = it[ARTICLES.DESCRIPTION],
                    body = it[ARTICLES.BODY],
                    createdAt = it[ARTICLES.CREATED_AT],
                    updatedAt = it[ARTICLES.UPDATED_AT]
                )
            }
        }.firstOrNull()
    }

}