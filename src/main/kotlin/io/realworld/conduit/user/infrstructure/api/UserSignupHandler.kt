package io.realworld.conduit.user.infrstructure.api

import at.favre.lib.crypto.bcrypt.BCrypt
import io.javalin.http.BadRequestResponse
import io.javalin.http.Context
import io.realworld.conduit.user.application.UserResponse
import io.realworld.conduit.user.application.UserResponseBody
import io.realworld.conduit.user.application.UserSignupBody
import io.realworld.conduit.user.domain.TokenCreator
import io.realworld.conduit.user.domain.User
import io.realworld.conduit.user.domain.UserId
import io.realworld.conduit.user.domain.UserRepository
import org.eclipse.jetty.http.HttpStatus

class UserSignupHandler(
    private val users: UserRepository,
    private val tokenCreator: TokenCreator
) {

    fun handle(ctx: Context) {
        val request = ctx.bodyValidator<UserSignupBody>().get().user

        val previousUser = users.byEmail(request.email)

        if (previousUser != null) {
            throw BadRequestResponse("Already existing user")
        }

        val password = BCrypt.withDefaults().hashToString(12, request.password.toCharArray())

        val user = User(
            id = UserId(),
            username = request.username,
            email = request.email,
            password = password
        )
        val newUser = users.insert(user)

        val token = tokenCreator.createFor(newUser.id)
        val userWithToken = newUser.copy(token = token)

        users.update(userWithToken)

        val response = UserResponseBody(
            UserResponse(
                email = userWithToken.email,
                username = userWithToken.username,
                image = userWithToken.image,
                bio = userWithToken.bio,
                token = userWithToken.mustHaveToken()
            )
        )

        ctx.status(HttpStatus.CREATED_201)
        ctx.json(response)
    }
}
