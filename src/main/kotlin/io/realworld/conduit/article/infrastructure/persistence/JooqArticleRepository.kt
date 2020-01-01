package io.realworld.conduit.article.infrastructure.persistence

import io.realworld.conduit.article.domain.Article
import io.realworld.conduit.article.domain.ArticleFilters
import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.generated.database.Tables.ARTICLES
import io.realworld.conduit.generated.database.Tables.FOLLOWS
import io.realworld.conduit.generated.database.Tables.USERS
import io.realworld.conduit.profile.infrastructure.persistence.profileFromRecord
import io.realworld.conduit.user.domain.UserId
import org.jooq.DSLContext
import org.jooq.Record

class JooqArticleRepository(private val ctx: DSLContext) : ArticleRepository {
    override fun bySlug(currentUserId: UserId?, slug: String): Article? {
        return ctx.select()
            .from(ARTICLES)
            .join(USERS).onKey()
            .leftJoin(FOLLOWS)
                .on(FOLLOWS.TO_USER_ID.eq(USERS.ID)
                    .and(FOLLOWS.FROM_USER_ID.eq(currentUserId?.value)))
            .fetchOne(::articleFromRecord)
    }

    override fun recent(currentUserId: UserId?, filters: ArticleFilters): List<Article> {
        return ctx.select()
            .from(ARTICLES)
            .join(USERS).onKey()
            .leftJoin(FOLLOWS)
                .on(FOLLOWS.TO_USER_ID.eq(USERS.ID)
                    .and(FOLLOWS.FROM_USER_ID.eq(currentUserId?.value)))
            .fetch(::articleFromRecord)
    }

    override fun insert(article: Article): Article {
        val articleId = ctx.insertInto(ARTICLES)
            .set(ARTICLES.TITLE, article.title)
            .set(ARTICLES.DESCRIPTION, article.description)
            .set(ARTICLES.BODY, article.body)
            .set(ARTICLES.SLUG, article.slug)
            .set(ARTICLES.AUTHOR_ID, article.author.id.value)
            .set(ARTICLES.CREATED_AT, article.createdAt)
            .returning()
            .fetchOne().id

        return article.copy(id = articleId)
    }
}

fun articleFromRecord(r: Record): Article {
    return Article(
        id = r[ARTICLES.ID],
        slug = r[ARTICLES.SLUG],
        title = r[ARTICLES.TITLE],
        description = r[ARTICLES.DESCRIPTION],
        body = r[ARTICLES.BODY],
        createdAt = r[ARTICLES.CREATED_AT],
        updatedAt = r[ARTICLES.UPDATED_AT],
        author = profileFromRecord(r)
    )
}
