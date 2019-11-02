package io.realworld.conduit.profile.application

import io.realworld.conduit.profile.domain.Profile
import io.realworld.conduit.user.domain.UserId

data class ViewProfileRequest(
    val username: String,
    val currentUserId: UserId?
)

data class ProfileResponseBody(
    val profile: Profile
)
