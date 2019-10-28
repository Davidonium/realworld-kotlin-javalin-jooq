package io.realworld.conduit.user.domain

interface UserRepository {
    fun byEmail(email: String): User?
    fun insert(user: User): User
    fun update(user: User)
}
