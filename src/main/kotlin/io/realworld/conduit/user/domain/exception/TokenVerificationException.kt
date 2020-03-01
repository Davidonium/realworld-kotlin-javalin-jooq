package io.realworld.conduit.user.domain.exception

import io.realworld.conduit.shared.domain.ConduitException


class TokenVerificationException(message: String, cause: Throwable? = null): ConduitException(message, cause)
