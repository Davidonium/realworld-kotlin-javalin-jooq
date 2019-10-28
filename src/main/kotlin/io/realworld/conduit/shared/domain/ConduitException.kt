package io.realworld.conduit.shared.domain

/**
 * Root application exception
 */
open class ConduitException(
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)
