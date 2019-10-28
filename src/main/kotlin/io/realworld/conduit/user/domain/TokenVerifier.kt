package io.realworld.conduit.user.domain

interface TokenVerifier {
    fun verify(token: String): TokenMetadata
}

data class TokenMetadata(
    val userId: UserId,
    val role: String
)
