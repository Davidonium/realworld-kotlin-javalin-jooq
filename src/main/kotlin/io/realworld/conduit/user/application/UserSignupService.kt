package io.realworld.conduit.user.application

import io.javalin.http.BadRequestResponse
import io.realworld.conduit.user.domain.PasswordHasher
import io.realworld.conduit.user.domain.TokenCreator
import io.realworld.conduit.user.domain.User
import io.realworld.conduit.user.domain.UserId
import io.realworld.conduit.user.domain.UserRepository


class UserSignupService(
    private val users: UserRepository,
    private val tokenCreator: TokenCreator,
    private val passwordHasher: PasswordHasher
) {
    fun execute(request: UserSignupRequest): UserResponse {
        val previousUser = users.byEmail(request.email)

        if (previousUser != null) {
            throw BadRequestResponse("Already existing user")
        }

        val password = passwordHasher.hash(request.password)

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


        return UserResponse(
            email = userWithToken.email,
            username = userWithToken.username,
            image = userWithToken.image,
            bio = userWithToken.bio,
            token = userWithToken.mustHaveToken()
        )
    }
}
