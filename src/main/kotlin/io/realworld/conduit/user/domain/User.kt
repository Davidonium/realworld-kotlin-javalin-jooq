package io.realworld.conduit.user.domain

import io.realworld.conduit.user.domain.exception.MissingTokenException

data class UserId(val value: Int? = null) {
    fun requiredValue(): Int {
        return value!!
    }
}

data class User(
    val id: UserId,
    val username: String,
    val email: String,
    val password: String,
    val token: String? = null,
    val bio: String? = null,
    val image: String? = null
) {
    fun mustHaveToken(): String {
        return token ?: throw MissingTokenException()
    }
}
