package io.realworld.conduit.article.infrastructure.persistence

import io.realworld.conduit.article.domain.Article
import io.realworld.conduit.article.domain.ArticleFilters
import io.realworld.conduit.article.domain.ArticleId
import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.article.domain.Tag
import io.realworld.conduit.generated.database.tables.references.*
import io.realworld.conduit.profile.infrastructure.persistence.profileFromRecord
import org.jooq.Condition
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.jooq.Table
import org.jooq.impl.DSL

class JooqArticleRepository(private val ctx: DSLContext) : ArticleRepository {
    override fun bySlug(slug: String): Article? {
        val article = ctx
            .select()
            .from(ARTICLES)
            .join(USERS).on(USERS.ID.eq(ARTICLES.AUTHOR_ID))
            .where(ARTICLES.SLUG.eq(slug))
            .fetchOne()
            ?: return null

        val tags = ctx
            .select(*TAGS.fields())
            .from(ARTICLE_TO_TAG)
            .join(TAGS).on(TAGS.ID.eq(ARTICLE_TO_TAG.TAG_ID))
            .where(ARTICLE_TO_TAG.ARTICLE_ID.eq(article[ARTICLES.ID]))
            .fetch()

        return mapArticle(article, tags)
    }

    override fun recent(filters: ArticleFilters): List<Article> {
        var from = ARTICLES
            .join(USERS).on(USERS.ID.eq(ARTICLES.AUTHOR_ID))

        var where: Condition = DSL.noCondition()

        if (filters.author != null) {
            where = where.and(USERS.USERNAME.eq(filters.author))
        }

        if (filters.tag != null) {
            from = from
                .join(ARTICLE_TO_TAG).on(ARTICLE_TO_TAG.ARTICLE_ID.eq(ARTICLES.ID))
                .join(TAGS).on(TAGS.ID.eq(ARTICLE_TO_TAG.TAG_ID)).and(TAGS.NAME.eq(filters.tag))
        }

        if (filters.favorited != null) {
            val favoritedUsers = USERS.`as`("fu")
            from = from
                .join(FAVORITED_ARTICLES).on(FAVORITED_ARTICLES.ARTICLE_ID.eq(ARTICLES.ID))
                .join(favoritedUsers).on(favoritedUsers.ID.eq(FAVORITED_ARTICLES.USER_ID))
            where = where.and(favoritedUsers.USERNAME.eq(filters.favorited))
        }

        val articles = ctx
            .selectDistinct(
                *ARTICLES.fields(),
                *USERS.fields()
            )
            .from(from)
            .where(where)
            .orderBy(ARTICLES.CREATED_AT.desc())
            .limit(filters.limit)
            .offset(filters.offset)
            .fetch()

        // fetched separatedly to avoid cartesian product of body and description
        val tags = ctx
            .select(ARTICLE_TO_TAG.ARTICLE_ID, *TAGS.fields())
            .from(ARTICLE_TO_TAG)
            .join(TAGS).on(TAGS.ID.eq(ARTICLE_TO_TAG.TAG_ID))
            .where(ARTICLE_TO_TAG.ARTICLE_ID.`in`(articles.map { it[ARTICLES.ID] }))
            .fetchGroups(ARTICLE_TO_TAG.ARTICLE_ID)

        return articles.map {
            mapArticle(it, tags[it[ARTICLES.ID]] ?: ctx.newResult())
        }
    }

    override fun recentCount(filters: ArticleFilters): Int {

        var from: Table<out Record> = ARTICLES

        var where = DSL.noCondition()

        if (filters.author != null) {
            from = from.join(USERS).on(USERS.ID.eq(ARTICLES.AUTHOR_ID))
            where = where.and(USERS.USERNAME.eq(filters.author))
        }

        if (filters.tag != null) {
            from = from
                .join(ARTICLE_TO_TAG).on(ARTICLE_TO_TAG.ARTICLE_ID.eq(ARTICLES.ID))
                .join(TAGS).on(TAGS.ID.eq(ARTICLE_TO_TAG.TAG_ID)).and(TAGS.NAME.eq(filters.tag))
        }

        if (filters.favorited != null) {
            val favoritedUsers = USERS.`as`("fu")
            from = from
                .join(FAVORITED_ARTICLES).on(FAVORITED_ARTICLES.ARTICLE_ID.eq(ARTICLES.ID))
                .join(favoritedUsers).on(favoritedUsers.ID.eq(FAVORITED_ARTICLES.USER_ID))

            where = where.and(favoritedUsers.USERNAME.eq(filters.favorited))
        }

        return ctx
            .selectCount()
            .from(from)
            .where(where)
            .fetchSingle().value1()
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

        return article.withId(id = ArticleId(articleRecord.id!!))
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

    private fun mapArticle(r: Record, tagRecords: Result<Record>): Article {
        return Article(
            id = ArticleId(r[ARTICLES.ID]!!),
            slug = r[ARTICLES.SLUG]!!,
            title = r[ARTICLES.TITLE]!!,
            description = r[ARTICLES.DESCRIPTION]!!,
            body = r[ARTICLES.BODY]!!,
            createdAt = r[ARTICLES.CREATED_AT]!!,
            updatedAt = r[ARTICLES.UPDATED_AT]!!,
            author = profileFromRecord(r),
            tags = tagRecords.map { Tag(it[TAGS.ID]!!, it[TAGS.NAME]!!) }
        )
    }
}
