package io.realworld.conduit.user.domain

data class User(
    val id: Int,
    val email: String,
    val password: String,
    val token: String,
    val bio: String,
    val image: String
)
