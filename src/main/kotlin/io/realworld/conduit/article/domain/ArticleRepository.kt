package io.realworld.conduit.article.domain

interface ArticleRepository {
    fun bySlug(slug: String): Article?
    fun all(): List<Article>
}
