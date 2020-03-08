package io.realworld.conduit.article.infrastructure.injection

import io.realworld.conduit.article.application.CreateArticleService
import io.realworld.conduit.article.application.ViewRecentArticlesService
import io.realworld.conduit.article.application.ViewTagsService
import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.article.domain.TagRepository
import io.realworld.conduit.article.infrastructure.api.CreateArticleHandler
import io.realworld.conduit.article.infrastructure.api.RecentArticlesHandler
import io.realworld.conduit.article.infrastructure.api.TagsHandler
import io.realworld.conduit.article.infrastructure.persistence.JooqArticleRepository
import io.realworld.conduit.article.infrastructure.persistence.JooqTagRepository
import org.koin.dsl.module

val articleModule = module {
    single { RecentArticlesHandler(get()) }
    single { CreateArticleHandler(get()) }
    single<ArticleRepository> { JooqArticleRepository(get()) }
    single<TagRepository> { JooqTagRepository(get()) }
    single { CreateArticleService(get(), get(), get(), get()) }
    single { ViewRecentArticlesService(get()) }
    single { TagsHandler(get()) }
    single { ViewTagsService(get()) }
}
