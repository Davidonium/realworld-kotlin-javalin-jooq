package io.realworld.conduit.user.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.shared.domain.ConduitException
import io.realworld.conduit.user.domain.UserId

fun Context.requireCurrentUserId(): UserId {
    return currentUserId() ?: throw ConduitException("User not logged in")
}

fun Context.currentUserId(): UserId? {
    return attribute("userId")
}
