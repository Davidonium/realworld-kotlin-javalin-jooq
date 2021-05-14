package io.realworld.conduit.article.infrastructure.persistence

import io.realworld.conduit.article.domain.Tag
import io.realworld.conduit.article.domain.TagRepository
import io.realworld.conduit.generated.database.tables.references.*
import org.jooq.DSLContext
import org.jooq.Record

class JooqTagRepository(private val ctx: DSLContext) : TagRepository {
    override fun all(): List<Tag> {
        return ctx.select()
            .from(TAGS)
            .fetch { tagFromRecord(it) }
    }

    override fun allByNames(names: List<String>): List<Tag> {
        return ctx.select()
            .from(TAGS)
            .where(TAGS.NAME.`in`(names))
            .fetch { tagFromRecord(it) }
    }

    override fun insert(tag: Tag): Tag {
        val id = ctx.insertInto(TAGS)
            .set(TAGS.NAME, tag.name)
            .returning()
            .fetchOne()!!.id

        return tag.copy(id = id!!)
    }

    override fun byName(name: String): Tag? {
        return ctx.select()
            .from(TAGS)
            .where(TAGS.NAME.eq(name))
            .fetchOne { tagFromRecord(it) }
    }
}

private fun tagFromRecord(r: Record) = Tag(r[TAGS.ID]!!, r[TAGS.NAME]!!)
