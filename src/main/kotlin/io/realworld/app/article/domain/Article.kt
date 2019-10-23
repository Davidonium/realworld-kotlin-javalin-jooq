package io.realworld.app.article.domain

import java.time.OffsetDateTime

data class Article(
    val id: Int,
    val slug: String,
    val title: String,
    val description: String,
    val body: String,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime?
)
