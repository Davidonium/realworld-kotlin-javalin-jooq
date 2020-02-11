package io.realworld.conduit.user.infrastructure.auth

import com.auth0.jwt.interfaces.JWTVerifier
import io.realworld.conduit.user.domain.TokenInfo
import io.realworld.conduit.user.domain.TokenVerifier
import io.realworld.conduit.user.domain.UserId

class JWTTokenVerifier(private val verifier: JWTVerifier) : TokenVerifier {
    override fun verify(token: String): TokenInfo {
        val jwt = verifier.verify(token)
        return TokenInfo(
            UserId(jwt.subject.toInt()),
            jwt.getClaim("role").asString()
        )
    }
}
