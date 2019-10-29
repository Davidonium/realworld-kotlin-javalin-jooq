package io.realworld.conduit.user.infrstructure.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import io.realworld.conduit.user.domain.PasswordHasher


class BCryptPasswordHasher : PasswordHasher {

    private val verifier = BCrypt.verifyer()
    private val hasher = BCrypt.withDefaults()

    override fun hash(password: String): String {
        return hasher.hashToString(12, password.toCharArray())
    }

    override fun verify(plainPassword: String, hashedPassword: String): Boolean {
        return verifier.verify(plainPassword.toCharArray(), hashedPassword.toCharArray()).verified
    }
}
