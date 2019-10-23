package io.realworld.conduit.article.infrastructure.injection

import io.realworld.conduit.article.domain.ArticleRepository
import io.realworld.conduit.article.infrastructure.api.ArticleListHandler
import io.realworld.conduit.article.infrastructure.persistence.JooqArticleRepository
import org.koin.dsl.module


val articleModule = module {
    single { ArticleListHandler(get()) }
    single<ArticleRepository> { JooqArticleRepository(get()) }
}