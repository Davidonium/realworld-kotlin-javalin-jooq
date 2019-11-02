package io.realworld.conduit.profile.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.profile.application.ProfileResponseBody
import io.realworld.conduit.profile.application.ViewProfileRequest
import io.realworld.conduit.profile.application.ViewProfileService
import io.realworld.conduit.user.infrastructure.api.currentUserId

class ProfileHandler(private val viewProfileService: ViewProfileService) {
    fun handle(ctx: Context) {
        val request = ViewProfileRequest(
            currentUserId = ctx.currentUserId(),
            username = ctx.pathParam("username")
        )
        val profile = viewProfileService.execute(request)
        ctx.json(ProfileResponseBody(profile))
    }
}
