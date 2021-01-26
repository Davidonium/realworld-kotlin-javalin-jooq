package io.realworld.conduit.user.infrastructure.persistence

import io.realworld.conduit.generated.database.Tables.USERS
import io.realworld.conduit.user.domain.User
import io.realworld.conduit.user.domain.UserId
import io.realworld.conduit.user.domain.UserRepository
import io.realworld.conduit.user.domain.exception.UserNotFoundException
import org.jooq.DSLContext
import org.jooq.Record

class JooqUserRepository(private val ctx: DSLContext) : UserRepository {

    override fun byId(userId: UserId): User {
        return ctx.select()
            .from(USERS)
            .where(USERS.ID.eq(userId.value))
            .fetchOne(::userFromRecord) ?: throw UserNotFoundException()
    }

    override fun byEmail(email: String): User? {
        return ctx.select()
            .from(USERS)
            .where(USERS.EMAIL.eq(email))
            .fetchOne(::userFromRecord) ?: throw UserNotFoundException()
    }

    override fun insert(user: User): User {
        val id = ctx.insertInto(USERS)
            .set(USERS.EMAIL, user.email)
            .set(USERS.USERNAME, user.username)
            .set(USERS.PASSWORD, user.password)
            .returning(USERS.ID)
            .fetchOne()!![USERS.ID]

        return user.withId(UserId(id))
    }

    override fun update(user: User) {
        ctx.update(USERS)
            .set(USERS.USERNAME, user.username)
            .set(USERS.BIO, user.bio)
            .set(USERS.IMAGE, user.image)
            .where(USERS.ID.eq(user.id.value))
            .execute()
    }
}

private fun userFromRecord(r: Record) =
    User(
        id = UserId(r[USERS.ID]),
        username = r[USERS.USERNAME],
        email = r[USERS.EMAIL],
        password = r[USERS.PASSWORD],
        bio = r[USERS.BIO],
        image = r[USERS.IMAGE]
    )
