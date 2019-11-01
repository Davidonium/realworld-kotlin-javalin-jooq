package io.realworld.conduit.user.domain

interface UserRepository {
    fun byId(userId: UserId): User
    fun byEmail(email: String): User?
    fun insert(user: User): User
    fun update(user: User)
}
