package io.realworld.conduit.user.application

import io.realworld.conduit.user.domain.User
import io.realworld.conduit.user.domain.UserId

data class UserResponseBody(
    val user: UserResponse
)

data class UserResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String?,
    val image: String?
) {
    companion object {
        fun fromUser(user: User, token: String) =
            UserResponse(
                email = user.email,
                token = token,
                username = user.username,
                bio = user.bio,
                image = user.image
            )
    }
}

data class UserSigninBody(
    val user: UserSigninRequest
)

data class UserSigninRequest(
    val email: String,
    val password: String
)

data class UserSignupBody(
    val user: UserSignupRequest
)

data class UserSignupRequest(
    val username: String,
    val email: String,
    val password: String
)

data class ViewCurrentUserRequest(
    val userId: UserId,
    val token: String
)

data class UpdateUserRequestBody(
    val user: UpdateUserRequest
)

data class UpdateUserRequest(
    val email: String,
    val token: String,
    val username: String,
    val bio: String?,
    val image: String?
)
