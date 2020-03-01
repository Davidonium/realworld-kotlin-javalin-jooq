package io.realworld.conduit.user.infrastructure.injection

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import io.realworld.conduit.user.application.UpdateCurrentUserService
import io.realworld.conduit.user.application.UserSigninService
import io.realworld.conduit.user.application.UserSignupService
import io.realworld.conduit.user.application.ViewCurrentUserService
import io.realworld.conduit.user.domain.PasswordHasher
import io.realworld.conduit.user.domain.TokenCreator
import io.realworld.conduit.user.domain.TokenVerifier
import io.realworld.conduit.user.domain.UserRepository
import io.realworld.conduit.user.infrastructure.api.TokenAccessManager
import io.realworld.conduit.user.infrastructure.api.CurrentUserHandler
import io.realworld.conduit.user.infrastructure.api.UpdateCurrentUserHandler
import io.realworld.conduit.user.infrastructure.api.UserSigninHandler
import io.realworld.conduit.user.infrastructure.api.UserSignupHandler
import io.realworld.conduit.user.infrastructure.auth.BCryptPasswordHasher
import io.realworld.conduit.user.infrastructure.auth.JWTTokenCreator
import io.realworld.conduit.user.infrastructure.auth.JWTTokenVerifier
import io.realworld.conduit.user.infrastructure.persistence.JooqUserRepository
import org.koin.dsl.module

val userModule = module {
    single { UserSignupHandler(get()) }
    single { UserSigninHandler(get()) }
    single { CurrentUserHandler(get()) }
    single { UpdateCurrentUserHandler(get()) }
    single<UserRepository> { JooqUserRepository(get()) }
    single<TokenVerifier> { JWTTokenVerifier(get()) }
    single<TokenCreator> { JWTTokenCreator(get()) }
    single { Algorithm.HMAC256("conduit") }
    single<JWTVerifier> {
        JWT.require(get())
        .withIssuer("conduit")
        .build()
    }
    single { TokenAccessManager(get()) }
    single<PasswordHasher> { BCryptPasswordHasher() }
    single { UserSignupService(get(), get(), get()) }
    single { UserSigninService(get(), get(), get()) }
    single { ViewCurrentUserService(get()) }
    single { UpdateCurrentUserService(get()) }
}
