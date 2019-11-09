package io.realworld.conduit.profile.infrastructure.injection

import io.realworld.conduit.profile.application.FollowProfileService
import io.realworld.conduit.profile.application.UnfollowProfileService
import io.realworld.conduit.profile.application.ViewProfileService
import io.realworld.conduit.profile.domain.ProfileRepository
import io.realworld.conduit.profile.infrastructure.api.FollowProfileHandler
import io.realworld.conduit.profile.infrastructure.api.ProfileHandler
import io.realworld.conduit.profile.infrastructure.api.UnfollowProfileHandler
import io.realworld.conduit.profile.infrastructure.persistence.JooqProfileRepository
import org.koin.dsl.module

val profileModule = module {
    single { ProfileHandler(get()) }
    single { FollowProfileHandler(get()) }
    single { UnfollowProfileHandler(get()) }
    single<ProfileRepository> { JooqProfileRepository(get()) }
    single { ViewProfileService(get()) }
    single { FollowProfileService(get()) }
    single { UnfollowProfileService(get()) }
}
