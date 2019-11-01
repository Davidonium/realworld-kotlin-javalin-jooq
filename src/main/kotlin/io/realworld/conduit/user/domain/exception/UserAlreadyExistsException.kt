package io.realworld.conduit.user.domain.exception

import io.realworld.conduit.shared.domain.ConduitException


class UserAlreadyExistsException : ConduitException("User already exists")
