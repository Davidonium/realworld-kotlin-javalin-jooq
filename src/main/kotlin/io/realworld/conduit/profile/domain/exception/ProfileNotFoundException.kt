package io.realworld.conduit.profile.domain.exception

import io.realworld.conduit.shared.domain.ResourceNotFoundException

class ProfileNotFoundException : ResourceNotFoundException("Profile was not found")
