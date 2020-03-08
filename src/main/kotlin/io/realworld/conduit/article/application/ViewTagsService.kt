package io.realworld.conduit.article.application

import io.realworld.conduit.article.domain.TagRepository

class ViewTagsService(private val tags: TagRepository) {
    fun execute(): List<String> {
        return tags.all().map { it.name }
    }
}
