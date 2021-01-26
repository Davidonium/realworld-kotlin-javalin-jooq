package io.realworld.conduit.user.domain.exception

import io.realworld.conduit.shared.domain.ConduitException

class UserNotFoundException : ConduitException("User not found")
