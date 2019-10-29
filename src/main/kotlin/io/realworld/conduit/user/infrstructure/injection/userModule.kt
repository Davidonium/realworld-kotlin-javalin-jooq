package io.realworld.conduit.user.infrstructure.injection

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import io.realworld.conduit.user.application.UserSignupService
import io.realworld.conduit.user.domain.PasswordHasher
import io.realworld.conduit.user.domain.TokenCreator
import io.realworld.conduit.user.domain.TokenVerifier
import io.realworld.conduit.user.domain.UserRepository
import io.realworld.conduit.user.infrstructure.api.AuthenticationAccessManager
import io.realworld.conduit.user.infrstructure.api.UserSigninHandler
import io.realworld.conduit.user.infrstructure.api.UserSignupHandler
import io.realworld.conduit.user.infrstructure.auth.BCryptPasswordHasher
import io.realworld.conduit.user.infrstructure.auth.JWTTokenCreator
import io.realworld.conduit.user.infrstructure.auth.JWTTokenVerifier
import io.realworld.conduit.user.infrstructure.persistence.JooqUserRepository
import org.koin.dsl.module

val userModule = module {
    single { UserSignupHandler(get()) }
    single { UserSigninHandler(get(), get()) }
    single<UserRepository> { JooqUserRepository(get()) }
    single<TokenVerifier> { JWTTokenVerifier(get()) }
    single<TokenCreator> { JWTTokenCreator(get()) }
    single { Algorithm.HMAC256("conduit") }
    single<JWTVerifier> {
        JWT.require(get())
        .withIssuer("conduit")
        .build()
    }
    single { AuthenticationAccessManager(get()) }
    single<PasswordHasher> { BCryptPasswordHasher() }
    single { UserSignupService(get(), get(), get())}
}
