package io.realworld.conduit.article.infrastructure.persistence

import io.realworld.conduit.article.domain.Article
import io.realworld.conduit.article.domain.ArticleFilters
import io.realworld.conduit.article.domain.ArticleId
import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.generated.database.Tables.ARTICLES
import io.realworld.conduit.generated.database.Tables.ARTICLE_TO_TAG
import io.realworld.conduit.generated.database.Tables.TAGS
import io.realworld.conduit.generated.database.Tables.USERS
import io.realworld.conduit.profile.infrastructure.persistence.profileFromRecord
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.impl.DSL

class JooqArticleRepository(private val ctx: DSLContext) : ArticleRepository {
    override fun bySlug(slug: String): Article? {
        return ctx
            .select()
            .from(ARTICLES)
            .join(USERS).onKey()
            .fetchOne(::articleFromRecord)
    }

    override fun recent(filters: ArticleFilters): List<Article> {

        val tagQuery = ctx
            .select(TAGS.NAME)
            .from(TAGS)
            .join(ARTICLE_TO_TAG).on(ARTICLE_TO_TAG.TAG_ID.eq(TAGS.ID))
            .where(ARTICLE_TO_TAG.ARTICLE_ID.eq(ARTICLES.ID))
            .asField<IntArray>("tags")

        return ctx
            .select(
                *ARTICLES.fields(),
                *USERS.fields(),
                tagQuery
            )
            .from(ARTICLES)
            .join(USERS).onKey()
            .fetch(::articleFromRecord)
    }

    override fun recentCount(filters: ArticleFilters): Int {
        return ctx
            .selectCount()
            .from(ARTICLES)
            .join(USERS).onKey()
            .fetchSingle(0, Int::class.java)
    }

    override fun insert(article: Article): Article {
        val articleRecord = ctx.newRecord(ARTICLES).apply {
            title = article.title
            description = article.description
            body = article.body
            slug = article.slug
            authorId = article.author.id.value
            createdAt = article.createdAt
        }
        articleRecord.store()

        return article.withId(id = ArticleId(articleRecord.id))
    }

    override fun assignTags(article: Article) {
        ctx.transaction { configuration ->
            val context = DSL.using(configuration)

            context
                .deleteFrom(ARTICLE_TO_TAG)
                .where(ARTICLE_TO_TAG.ARTICLE_ID.eq(article.id.value))

            article.tags.forEach { tag ->
                val articleToTag = context.newRecord(ARTICLE_TO_TAG).apply {
                    articleId = article.id.value
                    tagId = tag.id
                }
                articleToTag.store()
            }
        }
    }
}

fun articleFromRecord(r: Record): Article {
    return Article(
        id = ArticleId(r[ARTICLES.ID]),
        slug = r[ARTICLES.SLUG],
        title = r[ARTICLES.TITLE],
        description = r[ARTICLES.DESCRIPTION],
        body = r[ARTICLES.BODY],
        createdAt = r[ARTICLES.CREATED_AT],
        updatedAt = r[ARTICLES.UPDATED_AT],
        author = profileFromRecord(r)
    )
}
