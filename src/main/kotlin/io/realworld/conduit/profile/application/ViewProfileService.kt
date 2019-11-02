package io.realworld.conduit.profile.application

import io.realworld.conduit.profile.domain.Profile
import io.realworld.conduit.profile.domain.ProfileRepository

class ViewProfileService(private val profiles: ProfileRepository) {
    fun execute(request: ViewProfileRequest): Profile {
        return profiles.byUsername(request.currentUserId, request.username)
    }
}
