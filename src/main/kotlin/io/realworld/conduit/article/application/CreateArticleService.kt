package io.realworld.conduit.article.application

import io.realworld.conduit.article.domain.Article
import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.article.domain.TagRepository
import io.realworld.conduit.profile.application.ProfileResponse
import io.realworld.conduit.profile.domain.ProfileFactory
import io.realworld.conduit.shared.domain.SlugGenerator
import io.realworld.conduit.user.domain.UserRepository
import java.time.OffsetDateTime

class CreateArticleService(
    private val articles: ArticleRepository,
    private val users: UserRepository,
    private val tags: TagRepository,
    private val slugGenerator: SlugGenerator,
    private val profileFactory: ProfileFactory
) {

    fun execute(request: CreateArticleRequest): ArticleResponse {
        val currentUser = users.byId(request.currentUserId)
        val currentUserProfile = profileFactory.fromUser(currentUser)

        val article = Article(
            id = 0,
            slug = slugGenerator.generate(request.article.title),
            title = request.article.title,
            description = request.article.description,
            body = request.article.body,
            createdAt = OffsetDateTime.now(),
            author = currentUserProfile
        )
        val newArticle = articles.insert(article)

        return ArticleResponse(
            slug = newArticle.slug,
            title = newArticle.title,
            description = newArticle.description,
            body = newArticle.body,
            tagList = request.article.tagList,
            createdAt = newArticle.createdAt,
            updatedAt = newArticle.updatedAt,
            favorited = false, // TODO
            favoritesCount = 0, // TODO
            author = ProfileResponse.fromProfile(currentUserProfile)
        )
    }
}
