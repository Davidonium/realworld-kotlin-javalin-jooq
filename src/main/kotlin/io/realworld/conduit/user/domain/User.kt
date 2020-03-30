package io.realworld.conduit.user.domain

data class UserId(val value: Int? = null) {
    fun requiredValue(): Int {
        return value!!
    }
}

class User(
    val id: UserId,
    var username: String,
    var email: String,
    val password: String,
    var bio: String? = null,
    var image: String? = null
) {
    fun withId(newId: UserId): User {
        return User(
            id = newId,
            username = username,
            email = email,
            password = password,
            bio = bio,
            image = image
        )
    }
}
