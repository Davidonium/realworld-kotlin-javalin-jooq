package io.realworld.conduit.user.application

import io.realworld.conduit.user.domain.PasswordHasher
import io.realworld.conduit.user.domain.TokenCreator
import io.realworld.conduit.user.domain.UserRepository
import io.realworld.conduit.user.domain.exception.InvalidSigninException

class UserSigninService(
    private val users: UserRepository,
    private val passwordHasher: PasswordHasher,
    private val tokenCreator: TokenCreator
) {
    fun execute(request: UserSigninRequest): UserResponse {
        val user = users.byEmail(request.email) ?: throw InvalidSigninException()

        if (!passwordHasher.verify(request.password, user.password)) {
            throw InvalidSigninException()
        }

        val token = tokenCreator.createFor(user.id)

        return UserResponse.fromUser(user, token)
    }
}
