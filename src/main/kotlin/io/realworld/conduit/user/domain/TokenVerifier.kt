package io.realworld.conduit.user.domain

interface TokenVerifier {
    fun verify(token: String): TokenInfo
}

data class TokenInfo(
    val userId: UserId
)
