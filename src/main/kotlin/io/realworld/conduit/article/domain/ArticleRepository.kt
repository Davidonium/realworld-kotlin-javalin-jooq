package io.realworld.conduit.article.domain

import io.realworld.conduit.user.domain.UserId

interface ArticleRepository {
    fun bySlug(currentUserId: UserId?, slug: String): Article?
    fun recent(currentUserId: UserId?, filters: ArticleFilters): List<Article>
    fun insert(article: Article): Article
}
