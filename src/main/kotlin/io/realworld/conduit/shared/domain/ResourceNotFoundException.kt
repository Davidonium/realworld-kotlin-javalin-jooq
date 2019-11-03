package io.realworld.conduit.shared.domain

open class ResourceNotFoundException(
    override val message: String = "Resource not found"
) : ConduitException(message)
