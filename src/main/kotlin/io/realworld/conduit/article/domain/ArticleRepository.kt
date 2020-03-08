package io.realworld.conduit.article.domain

interface ArticleRepository {
    fun bySlug(slug: String): Article?
    fun recent(filters: ArticleFilters): List<Article>
    fun recentCount(filters: ArticleFilters): Int
    fun insert(article: Article): Article
    fun assignTags(article: Article)
}
