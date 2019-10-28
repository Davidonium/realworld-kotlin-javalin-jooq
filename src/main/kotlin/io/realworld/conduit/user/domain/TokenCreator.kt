package io.realworld.conduit.user.domain

interface TokenCreator {
    fun createFor(userId: UserId): String
}
