package io.realworld.conduit.article.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.article.application.ViewTagsService

class TagsHandler(private val viewTagsService: ViewTagsService) {
    fun handle(ctx: Context) {
        ctx.json(TagsResponse(viewTagsService.execute()))
    }
}

data class TagsResponse(
    val tags: List<String>
)
