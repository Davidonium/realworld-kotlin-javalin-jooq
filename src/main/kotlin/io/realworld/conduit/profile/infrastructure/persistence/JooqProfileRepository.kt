package io.realworld.conduit.profile.infrastructure.persistence

import io.realworld.conduit.generated.database.Tables.FOLLOWS
import io.realworld.conduit.generated.database.Tables.USERS
import io.realworld.conduit.profile.domain.Profile
import io.realworld.conduit.profile.domain.ProfileRepository
import io.realworld.conduit.profile.domain.exception.ProfileNotFoundException
import io.realworld.conduit.user.domain.UserId
import org.jooq.DSLContext
import org.jooq.Record

class JooqProfileRepository(private val ctx: DSLContext) : ProfileRepository {
    override fun byUsername(currentUserId: UserId?, username: String): Profile {
        return ctx.select()
            .from(USERS)
            .leftJoin(FOLLOWS)
                .on(FOLLOWS.TO_USER_ID.eq(USERS.ID)
                    .and(FOLLOWS.FROM_USER_ID.eq(currentUserId?.value)))
            .where(USERS.USERNAME.eq(username))
            .fetchOne(::profileFromRecord)
            ?: throw ProfileNotFoundException()
    }

    override fun follow(currentUserId: UserId, followedUserId: UserId) {
        ctx.insertInto(FOLLOWS)
            .set(FOLLOWS.FROM_USER_ID, currentUserId.requiredValue())
            .set(FOLLOWS.TO_USER_ID, followedUserId.requiredValue())
            .execute()
    }

    override fun unfollow(currentUserId: UserId, unFollowedUserId: UserId) {
        ctx.delete(FOLLOWS)
            .where(FOLLOWS.FROM_USER_ID.eq(currentUserId.requiredValue()))
            .and(FOLLOWS.TO_USER_ID.eq(unFollowedUserId.requiredValue()))
            .execute()
    }
}

fun profileFromRecord(r: Record): Profile {
    return Profile(
        id = UserId(r[USERS.ID]),
        username = r[USERS.USERNAME],
        bio = r[USERS.BIO],
        image = r[USERS.IMAGE]
    )
}
