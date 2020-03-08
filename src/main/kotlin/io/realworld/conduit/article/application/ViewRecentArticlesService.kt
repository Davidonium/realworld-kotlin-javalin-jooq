package io.realworld.conduit.article.application

import io.realworld.conduit.article.domain.ArticleFilters
import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.profile.application.ProfileResponse

class ViewRecentArticlesService(private val articles: ArticleRepository) {
    fun execute(request: ViewRecentArticlesRequest): ArticleListResponse {

        val filters = ArticleFilters(
            tag = request.tag,
            author = request.author,
            favorited = request.favorited,
            limit = request.limit,
            offset = request.offset
        )
        val articleList = articles.recent(filters)
        val articlesCount = articles.recentCount(filters)

        return ArticleListResponse(
            articles = articleList.map { article ->
                ArticleResponse(
                    slug = article.slug,
                    title = article.title,
                    description = article.description,
                    body = article.body,
                    tagList = article.tags.map { it.name },
                    createdAt = article.createdAt,
                    updatedAt = article.updatedAt,
                    favorited = false, // TODO calculate this
                    favoritesCount = 0, // TODO calculate this
                    author = ProfileResponse.fromProfile(article.author)
                )
            },
            articlesCount = articlesCount
        )
    }
}
