package io.realworld.conduit.user.infrstructure.api

import at.favre.lib.crypto.bcrypt.BCrypt
import io.javalin.http.Context
import io.realworld.conduit.user.application.UserResponse
import io.realworld.conduit.user.application.UserResponseBody
import io.realworld.conduit.user.application.UserSigninBody
import io.realworld.conduit.user.domain.TokenCreator
import io.realworld.conduit.user.domain.UserRepository
import io.realworld.conduit.user.domain.exception.InvalidSigninException

class UserSigninHandler(
    private val users: UserRepository,
    private val tokenCreator: TokenCreator
) {

    fun handle(ctx: Context) {
        val request = ctx.bodyValidator<UserSigninBody>().get().user

        val user = users.byEmail(request.email) ?: throw InvalidSigninException()

        val result = BCrypt.verifyer().verify(request.password.toCharArray(), user.password.toCharArray())

        if (!result.verified) {
            throw InvalidSigninException()
        }

        val token = tokenCreator.createFor(user.id)

        val userWithToken = user.copy(token = token)
        users.update(userWithToken)

        val response = UserResponseBody(
            UserResponse(
                email = userWithToken.email,
                token = userWithToken.mustHaveToken(),
                username = userWithToken.username,
                bio = userWithToken.bio,
                image = userWithToken.image
            )
        )
        ctx.json(response)
    }
}
