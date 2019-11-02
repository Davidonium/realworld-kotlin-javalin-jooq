package io.realworld.conduit.profile.infrastructure.injection

import io.realworld.conduit.profile.application.ViewProfileService
import io.realworld.conduit.profile.domain.ProfileRepository
import io.realworld.conduit.profile.infrastructure.api.ProfileHandler
import io.realworld.conduit.profile.infrastructure.persistence.JooqProfileRepository
import org.koin.dsl.module

val profileModule = module {
    single { ProfileHandler(get()) }
    single<ProfileRepository> { JooqProfileRepository(get()) }
    single { ViewProfileService(get()) }
}
