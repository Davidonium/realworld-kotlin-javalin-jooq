package io.realworld.conduit.user.infrastructure.auth

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.JWTVerifier
import io.realworld.conduit.user.domain.TokenInfo
import io.realworld.conduit.user.domain.TokenVerifier
import io.realworld.conduit.user.domain.UserId
import io.realworld.conduit.user.domain.exception.TokenVerificationException

class JWTTokenVerifier(private val verifier: JWTVerifier) : TokenVerifier {
    override fun verify(token: String): TokenInfo {
        try {
            val jwt = verifier.verify(token)
            return TokenInfo(
                userId = UserId(jwt.subject.toInt())
            )
        } catch (e: JWTVerificationException) {
            throw TokenVerificationException("Token $token is not valid", e)
        }
    }
}
