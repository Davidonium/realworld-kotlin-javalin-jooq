package io.realworld.conduit.user.application

import io.realworld.conduit.user.domain.UserRepository

class ViewCurrentUserService(private val users: UserRepository) {
    fun execute(request: ViewCurrentUserRequest): UserResponse {
        val user = users.byId(request.userId)
        return UserResponse.fromUser(user, request.token)
    }
}
