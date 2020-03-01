package io.realworld.conduit.profile.domain

import io.realworld.conduit.user.domain.User

class ProfileFactory {
    fun fromUser(user: User): Profile {
        return Profile(
            id = user.id,
            username = user.username,
            bio = user.bio,
            image = user.image
        )
    }
}
