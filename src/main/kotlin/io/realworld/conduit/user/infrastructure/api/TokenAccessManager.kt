package io.realworld.conduit.user.infrastructure.api

import io.javalin.core.security.AccessManager
import io.javalin.core.security.Role
import io.javalin.http.Context
import io.javalin.http.Handler
import io.javalin.http.UnauthorizedResponse
import io.realworld.conduit.user.domain.TokenVerifier

internal enum class Roles : Role {
    ANYONE, AUTH
}

class TokenAccessManager(private val tokenVerifier: TokenVerifier) : AccessManager {

    override fun manage(handler: Handler, ctx: Context, permittedRoles: MutableSet<Role>) {

        val token = ctx.token()

        val tokenInfo = token?.let { tokenVerifier.verify(it) }

        val isPublic = permittedRoles.size == 1 && permittedRoles.first() == Roles.ANYONE

        if (!isPublic && tokenInfo == null) {
            throw UnauthorizedResponse()
        }

        tokenInfo?.also {
            ctx.attribute("userId", it.userId)
        }

        handler.handle(ctx)
    }
}
