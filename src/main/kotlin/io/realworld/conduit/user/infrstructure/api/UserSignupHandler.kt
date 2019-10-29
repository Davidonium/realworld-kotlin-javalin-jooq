package io.realworld.conduit.user.infrstructure.api

import io.javalin.http.Context
import io.realworld.conduit.user.application.UserResponseBody
import io.realworld.conduit.user.application.UserSignupBody
import io.realworld.conduit.user.application.UserSignupService
import org.eclipse.jetty.http.HttpStatus

class UserSignupHandler(
    private val userSignupService: UserSignupService
) {

    fun handle(ctx: Context) {
        val request = ctx.bodyValidator<UserSignupBody>().get().user

        val userResponse = userSignupService.execute(request)
        val response = UserResponseBody(userResponse)

        ctx.status(HttpStatus.CREATED_201)
        ctx.json(response)
    }
}
