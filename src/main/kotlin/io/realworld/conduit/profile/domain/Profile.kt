package io.realworld.conduit.profile.domain

data class Profile(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
)
