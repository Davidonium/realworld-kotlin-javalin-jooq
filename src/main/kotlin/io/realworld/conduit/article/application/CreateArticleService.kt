package io.realworld.conduit.article.application

import io.realworld.conduit.article.domain.Article
import io.realworld.conduit.article.domain.ArticleId
import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.article.domain.Tag
import io.realworld.conduit.article.domain.TagRepository
import io.realworld.conduit.profile.application.ProfileResponse
import io.realworld.conduit.profile.domain.Profile
import io.realworld.conduit.shared.domain.SlugGenerator
import io.realworld.conduit.user.domain.UserRepository
import java.time.OffsetDateTime

class CreateArticleService(
    private val articles: ArticleRepository,
    private val tags: TagRepository,
    private val users: UserRepository,
    private val slugGenerator: SlugGenerator
) {

    fun execute(request: CreateArticleRequest): ArticleResponse {
        val currentUser = users.byId(request.currentUserId)
        val currentUserProfile = Profile.fromUser(currentUser)

        val finalTags = request.article.tagList.map { t ->
            tags.byName(t) ?: tags.insert(Tag(0, t))
        }

        val article = Article(
            id = ArticleId(),
            slug = slugGenerator.generate(request.article.title),
            title = request.article.title,
            description = request.article.description,
            body = request.article.body,
            createdAt = OffsetDateTime.now(),
            author = currentUserProfile,
            tags = finalTags
        )
        val newArticle = articles.insert(article)
        articles.assignTags(newArticle)

        return ArticleResponse(
            slug = newArticle.slug,
            title = newArticle.title,
            description = newArticle.description,
            body = newArticle.body,
            tagList = newArticle.tags.map { it.name },
            createdAt = newArticle.createdAt,
            updatedAt = newArticle.updatedAt,
            favorited = false, // TODO
            favoritesCount = 0, // TODO
            author = ProfileResponse.fromProfile(currentUserProfile)
        )
    }
}
