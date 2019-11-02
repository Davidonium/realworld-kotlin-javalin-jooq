package io.realworld.conduit.user.infrastructure.api

import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.ForbiddenResponse
import io.javalin.http.Handler
import io.realworld.conduit.user.domain.TokenVerifier

private const val AUTHORIZATION_HEADER = "Authorization"

internal enum class Roles : Role {
    ANYONE, AUTH
}

class AuthenticationAccessManager(private val tokenVerifier: TokenVerifier) : AccessManager {

    override fun manage(handler: Handler, ctx: Context, permittedRoles: MutableSet<Role>) {

        val authorization = ctx.header(AUTHORIZATION_HEADER)

        val tokenMetadata = authorization?.let {
            tokenVerifier.verify(it.replace("Token ", ""))
        }

        val role = tokenMetadata?.let {
            Roles.valueOf(it.role)
        } ?: Roles.ANYONE

        val isPublic = permittedRoles.size == 1 && permittedRoles.first() == Roles.ANYONE

        if (!isPublic && !permittedRoles.contains(role)) {
            throw ForbiddenResponse()
        }

        tokenMetadata?.let {
            ctx.attribute("userId", it.userId)
        }

        handler.handle(ctx)
    }
}
