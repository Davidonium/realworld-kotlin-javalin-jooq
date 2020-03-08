package io.realworld.conduit.article.domain

import io.realworld.conduit.profile.domain.Profile
import java.time.OffsetDateTime

data class ArticleId(val value: Int = 0)

class Article(
    val id: ArticleId,
    val slug: String,
    val title: String,
    val description: String,
    val body: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime? = null,
    val author: Profile,
    val tags: List<Tag> = listOf()
) {
    fun withId(id: ArticleId): Article {
        return Article(
            id = id,
            slug = slug,
            title = title,
            description = description,
            body = body,
            createdAt = createdAt,
            updatedAt = updatedAt,
            author = author,
            tags = tags
        )
    }
}
