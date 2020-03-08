package io.realworld.conduit.profile.domain

import io.realworld.conduit.user.domain.User
import io.realworld.conduit.user.domain.UserId

data class Profile(
    val id: UserId,
    val username: String,
    val bio: String?,
    val image: String?
) {
    companion object {
        fun fromUser(user: User): Profile {
            return Profile(
                id = user.id,
                username = user.username,
                bio = user.bio,
                image = user.image
            )
        }
    }
}
