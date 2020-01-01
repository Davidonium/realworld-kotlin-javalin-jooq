package io.realworld.conduit.article.domain

import io.realworld.conduit.profile.domain.Profile
import java.time.OffsetDateTime

data class Article(
    val id: Int,
    val slug: String,
    val title: String,
    val description: String,
    val body: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime? = null,
    val author: Profile,
    val tags: List<Tag> = listOf()
)
