package io.realworld.conduit.user.domain.exception

import io.realworld.conduit.shared.domain.ConduitException

class InvalidSigninException : ConduitException("Invalid username or password")
