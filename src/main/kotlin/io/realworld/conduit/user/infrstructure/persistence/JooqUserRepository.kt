package io.realworld.conduit.user.infrstructure.persistence

import io.realworld.conduit.generated.database.Tables.USERS
import io.realworld.conduit.user.domain.User
import io.realworld.conduit.user.domain.UserId
import io.realworld.conduit.user.domain.UserRepository
import org.jooq.DSLContext
import org.jooq.Record

class JooqUserRepository(private val db: DSLContext) : UserRepository {

    override fun byId(userId: UserId): User {
        return db.use { ctx ->
            ctx.select()
                .from(USERS)
                .where(USERS.ID.eq(userId.value))
                .fetchOne(::userFromRecord)
        }
    }

    override fun byEmail(email: String): User? {
        return db.use { ctx ->
            ctx.select()
                .from(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOne(::userFromRecord)
        }
    }

    override fun insert(user: User): User {
        val id = db.use {
            it.insertInto(USERS)
                .set(USERS.EMAIL, user.email)
                .set(USERS.USERNAME, user.username)
                .set(USERS.TOKEN, user.token)
                .set(USERS.PASSWORD, user.password)
                .returning(USERS.ID)
                .fetchOne()[USERS.ID]
        }

        return user.copy(id = UserId(id))
    }

    override fun update(user: User) {
        db.use {
            it.update(USERS)
                .set(USERS.USERNAME, user.username)
                .set(USERS.BIO, user.bio)
                .set(USERS.IMAGE, user.image)
                .set(USERS.TOKEN, user.token)
                .where(USERS.ID.eq(user.id.value))
                .execute()
        }
    }
}

private fun userFromRecord(r: Record) =
    User(
        id = UserId(r[USERS.ID]),
        username = r[USERS.USERNAME],
        email = r[USERS.EMAIL],
        password = r[USERS.PASSWORD],
        token = r[USERS.TOKEN],
        bio = r[USERS.BIO],
        image = r[USERS.IMAGE]
    )
