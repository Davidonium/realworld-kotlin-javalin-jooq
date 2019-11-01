package io.realworld.conduit.user.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.user.application.UserResponseBody
import io.realworld.conduit.user.application.ViewCurrentUserRequest
import io.realworld.conduit.user.application.ViewCurrentUserService

class CurrentUserHandler(private val viewCurrentUserService: ViewCurrentUserService) {
    fun handle(ctx: Context) {
        val request = ViewCurrentUserRequest(userId = ctx.currentUserId())
        val response = viewCurrentUserService.execute(request)
        ctx.json(UserResponseBody(user = response))
    }
}
