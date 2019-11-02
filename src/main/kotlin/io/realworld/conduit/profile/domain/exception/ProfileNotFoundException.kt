package io.realworld.conduit.profile.domain.exception

import io.realworld.conduit.shared.domain.ConduitException

class ProfileNotFoundException : ConduitException("Profile was not found")
