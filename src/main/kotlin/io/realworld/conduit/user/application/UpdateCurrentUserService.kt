package io.realworld.conduit.user.application

import io.realworld.conduit.user.domain.UserId
import io.realworld.conduit.user.domain.UserRepository

class UpdateCurrentUserService(private val users: UserRepository) {
    fun execute(currentUserId: UserId, request: UpdateUserRequest): UserResponse {
        val user = users.byId(currentUserId)

        user.apply {
            email = request.email
            username = request.username
            bio = request.bio
            image = request.image
        }
        users.update(user)
        return UserResponse.fromUser(user, request.token)
    }
}
