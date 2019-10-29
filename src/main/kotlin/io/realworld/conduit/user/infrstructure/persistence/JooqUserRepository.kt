package io.realworld.conduit.user.infrstructure.persistence

import io.realworld.conduit.generated.database.Tables.USERS
import io.realworld.conduit.user.domain.User
import io.realworld.conduit.user.domain.UserId
import io.realworld.conduit.user.domain.UserRepository
import org.jooq.DSLContext

class JooqUserRepository(private val db: DSLContext) : UserRepository {
    override fun byEmail(email: String): User? {
        return db.use { ctx ->
            ctx.select()
                .from(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOne {
                    User(
                        id = UserId(it[USERS.ID]),
                        username = it[USERS.USERNAME],
                        email = it[USERS.EMAIL],
                        password = it[USERS.PASSWORD],
                        token = it[USERS.TOKEN],
                        bio = it[USERS.BIO],
                        image = it[USERS.IMAGE]
                    )
                }
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
