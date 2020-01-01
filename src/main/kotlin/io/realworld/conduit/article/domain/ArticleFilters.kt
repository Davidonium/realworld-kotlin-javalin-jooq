package io.realworld.conduit.article.domain

data class ArticleFilters(
    val tag: String?,
    val author: String?,
    val favorited: String?,
    val limit: Int,
    val offset: Int
)
