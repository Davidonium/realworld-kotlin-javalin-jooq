package io.realworld.conduit.profile.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.profile.application.FollowProfileRequest
import io.realworld.conduit.profile.application.FollowProfileService
import io.realworld.conduit.profile.application.ProfileResponseBody
import io.realworld.conduit.user.infrastructure.api.requireCurrentUserId

class FollowProfileHandler(private val followProfileService: FollowProfileService) {
    fun handle(ctx: Context) {
        val request = FollowProfileRequest(
            currentUserId = ctx.requireCurrentUserId(),
            username = ctx.pathParam("username")
        )
        val response = followProfileService.execute(request)
        ctx.json(ProfileResponseBody(response))
    }
}
