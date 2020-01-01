package io.realworld.conduit.article.domain

interface TagRepository {
    fun all(): List<Tag>
    fun allByNames(names: List<String>): List<Tag>
    fun insert(tag: Tag): Tag
}
