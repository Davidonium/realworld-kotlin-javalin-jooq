package io.realworld.conduit.article.application

import io.realworld.conduit.profile.application.ProfileResponse
import io.realworld.conduit.user.domain.UserId
import java.time.OffsetDateTime

data class CreateArticleRequestBody(
    val article: CreateArticle
)

data class CreateArticleRequest(
    val currentUserId: UserId,
    val article: CreateArticle
)

data class CreateArticle(
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String> = listOf()
)

data class ViewRecentArticlesRequest(
    val currentUserId: UserId?,
    val tag: String?,
    val author: String?,
    val favorited: String?,
    val limit: Int = 20,
    val offset: Int = 0
)

data class ArticleResponseBody(
    val article: ArticleResponse
)

data class ArticleResponse(
    val slug: String,
    val title: String,
    val description: String,
    val body: String,
    val tagList: List<String>,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime?,
    val favorited: Boolean,
    val favoritesCount: Int,
    val author: ProfileResponse
)

data class ArticleListResponse(
    val articles: List<ArticleResponse>,
    val articlesCount: Int
)
