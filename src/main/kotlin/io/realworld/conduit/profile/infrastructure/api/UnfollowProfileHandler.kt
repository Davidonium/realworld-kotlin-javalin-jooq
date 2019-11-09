package io.realworld.conduit.profile.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.profile.application.ProfileResponseBody
import io.realworld.conduit.profile.application.UnfollowProfileRequest
import io.realworld.conduit.profile.application.UnfollowProfileService
import io.realworld.conduit.user.infrastructure.api.requireCurrentUserId

class UnfollowProfileHandler(private val unFollowProfileService: UnfollowProfileService) {
    fun handle(ctx: Context) {
        val request = UnfollowProfileRequest(
            currentUserId = ctx.requireCurrentUserId(),
            username = ctx.pathParam("username")
        )
        val response = unFollowProfileService.execute(request)
        ctx.json(ProfileResponseBody(response))
    }
}
