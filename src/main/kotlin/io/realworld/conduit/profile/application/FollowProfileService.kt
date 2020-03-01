package io.realworld.conduit.profile.application

import io.realworld.conduit.profile.domain.ProfileRepository

class FollowProfileService(private val profiles: ProfileRepository) {
    fun execute(request: FollowProfileRequest): ProfileResponse {
        val profileToFollow = profiles.byUsername(request.username)

        profiles.follow(request.currentUserId, profileToFollow.id)

        return ProfileResponse.fromProfile(profileToFollow).copy(following = true)
    }
}
