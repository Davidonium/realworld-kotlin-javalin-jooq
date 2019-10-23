package io.realworld.conduit.article.infrastructure.injection

import io.realworld.conduit.article.infrastructure.api.ArticleListHandler
import org.koin.dsl.module


val articleModule = module {
    single { ArticleListHandler(get()) }
}