package io.realworld.conduit.profile.application

import io.realworld.conduit.profile.domain.ProfileRepository

class ViewProfileService(private val profiles: ProfileRepository) {
    fun execute(request: ViewProfileRequest): ProfileResponse {
        val profile = profiles.byUsername(request.currentUserId, request.username)
        return ProfileResponse.fromProfile(profile)
    }
}
