package io.realworld.conduit.user.domain.exception

import io.realworld.conduit.shared.domain.ResourceNotFoundException

class UserNotFoundException : ResourceNotFoundException("User not found")
