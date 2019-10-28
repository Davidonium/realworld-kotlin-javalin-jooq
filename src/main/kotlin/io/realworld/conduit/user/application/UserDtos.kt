package io.realworld.conduit.user.application

data class UserResponseBody(
    val user: UserResponse
)

data class UserResponse(
    val email: String,
    val token: String,
    val username: String,
    val bio: String?,
    val image: String?
)

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
