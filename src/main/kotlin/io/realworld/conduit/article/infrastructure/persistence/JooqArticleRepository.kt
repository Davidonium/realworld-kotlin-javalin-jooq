package io.realworld.conduit.article.infrastructure.persistence

import io.realworld.conduit.article.domain.Article
import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.generated.database.Tables.ARTICLES
import org.jooq.DSLContext
import org.jooq.Record

class JooqArticleRepository(private val ctx: DSLContext) : ArticleRepository {
    override fun bySlug(slug: String): Article? {
        return ctx.select()
            .from(ARTICLES)
            .fetchOne(::articleFromRecord)
    }

    override fun all(): List<Article> {
        return ctx.select()
            .from(ARTICLES)
            .fetch(::articleFromRecord)
    }
}

private fun articleFromRecord(r: Record): Article {
    return Article(
        id = r[ARTICLES.ID],
        slug = r[ARTICLES.SLUG],
        title = r[ARTICLES.TITLE],
        description = r[ARTICLES.DESCRIPTION],
        body = r[ARTICLES.BODY],
        createdAt = r[ARTICLES.CREATED_AT],
        updatedAt = r[ARTICLES.UPDATED_AT]
    )
}
