package io.realworld.conduit.article.domain

interface TagRepository {
    fun all(): List<Tag>
    fun allByNames(names: List<String>): List<Tag>
    fun byName(name: String): Tag?
    fun insert(tag: Tag): Tag
}
