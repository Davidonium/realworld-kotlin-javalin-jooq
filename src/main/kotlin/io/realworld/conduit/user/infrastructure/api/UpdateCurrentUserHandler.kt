package io.realworld.conduit.user.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.user.application.UpdateCurrentUserService
import io.realworld.conduit.user.application.UpdateUserRequestBody
import io.realworld.conduit.user.application.UserResponseBody

class UpdateCurrentUserHandler(
    private val updateCurrentUserService: UpdateCurrentUserService
) {
    fun handle(ctx: Context) {
        val request = ctx.body<UpdateUserRequestBody>()
        val response = updateCurrentUserService.execute(ctx.requireCurrentUserId(), request.user)
        ctx.json(UserResponseBody(response))
    }
}
