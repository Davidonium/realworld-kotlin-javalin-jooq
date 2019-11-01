package io.realworld.conduit.user.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.user.application.UserResponseBody
import io.realworld.conduit.user.application.UserSigninBody
import io.realworld.conduit.user.application.UserSigninService

class UserSigninHandler(
    private val userSigninService: UserSigninService
) {

    fun handle(ctx: Context) {
        val request = ctx.bodyValidator<UserSigninBody>().get().user
        val userResponse = userSigninService.execute(request)
        val response = UserResponseBody(userResponse)
        ctx.json(response)
    }
}
