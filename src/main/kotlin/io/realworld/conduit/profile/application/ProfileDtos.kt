package io.realworld.conduit.profile.application

import io.realworld.conduit.profile.domain.Profile
import io.realworld.conduit.user.domain.UserId

data class ViewProfileRequest(
    val username: String,
    val currentUserId: UserId?
)

data class ProfileResponseBody(
    val profile: ProfileResponse
)

data class ProfileResponse(
    val username: String,
    val bio: String?,
    val image: String?,
    val following: Boolean
) {
    companion object {
        fun fromProfile(profile: Profile): ProfileResponse {
            return ProfileResponse(
                username = profile.username,
                bio = profile.bio,
                image = profile.image,
                following = profile.following
            )
        }
    }
}

data class FollowProfileRequest(
    val currentUserId: UserId,
    val username: String
)

data class UnfollowProfileRequest(
    val username: String,
    val currentUserId: UserId
)
