package io.realworld.conduit.profile.application

import io.realworld.conduit.profile.domain.ProfileRepository

class UnfollowProfileService(private val profiles: ProfileRepository) {

    fun execute(request: UnfollowProfileRequest): ProfileResponse {
        val profileToUnfollow = profiles.byUsername(request.username)

        profiles.unfollow(request.currentUserId, profileToUnfollow.id)

        return ProfileResponse.fromProfile(profileToUnfollow).copy(following = false)
    }
}
