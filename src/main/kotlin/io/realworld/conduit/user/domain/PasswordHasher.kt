package io.realworld.conduit.user.domain


interface PasswordHasher {
    fun hash(password: String): String
    fun verify(plainPassword: String, hashedPassword: String): Boolean
}
