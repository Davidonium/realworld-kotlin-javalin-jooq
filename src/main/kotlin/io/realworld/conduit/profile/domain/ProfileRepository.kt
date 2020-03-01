package io.realworld.conduit.profile.domain

import io.realworld.conduit.user.domain.UserId

interface ProfileRepository {
    fun byUsername(username: String): Profile
    fun follow(currentUserId: UserId, followedUserId: UserId)
    fun unfollow(currentUserId: UserId, unFollowedUserId: UserId)
}
