package io.realworld.conduit.user.infrstructure.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.realworld.conduit.user.domain.TokenCreator
import io.realworld.conduit.user.domain.UserId
import io.realworld.conduit.user.infrstructure.api.Roles
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date

class JWTTokenCreator(private val algorithm: Algorithm) : TokenCreator {
    override fun createFor(userId: UserId): String {
        return JWT.create()
            .withIssuer("conduit")
            .withExpiresAt(Date.from(Instant.now().plus(5, ChronoUnit.DAYS)))
            .withSubject(userId.value.toString())
            .withClaim("role", Roles.AUTH.toString())
            .sign(algorithm)
    }
}
