package io.realworld.conduit.profile.domain

import io.realworld.conduit.user.domain.UserId

data class Profile(
    val id: UserId,
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
)
