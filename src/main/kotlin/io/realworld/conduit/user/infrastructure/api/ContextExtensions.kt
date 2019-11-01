package io.realworld.conduit.user.infrastructure.api

import io.javalin.http.Context
import io.realworld.conduit.shared.domain.ConduitException
import io.realworld.conduit.user.domain.UserId

fun Context.currentUserId(): UserId {
    return attribute("userId") ?: throw ConduitException("User not logged in")
}
