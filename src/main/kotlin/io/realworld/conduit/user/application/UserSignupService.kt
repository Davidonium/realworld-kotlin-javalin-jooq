package io.realworld.conduit.user.application

import io.realworld.conduit.user.domain.PasswordHasher
import io.realworld.conduit.user.domain.TokenCreator
import io.realworld.conduit.user.domain.User
import io.realworld.conduit.user.domain.UserId
import io.realworld.conduit.user.domain.UserRepository
import io.realworld.conduit.user.domain.exception.UserAlreadyExistsException

class UserSignupService(
    private val users: UserRepository,
    private val tokenCreator: TokenCreator,
    private val passwordHasher: PasswordHasher
) {
    fun execute(request: UserSignupRequest): UserResponse {
        val previousUser = users.byEmail(request.email)

        if (previousUser != null) {
            throw UserAlreadyExistsException()
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

        return UserResponse.fromUser(userWithToken)
    }
}
