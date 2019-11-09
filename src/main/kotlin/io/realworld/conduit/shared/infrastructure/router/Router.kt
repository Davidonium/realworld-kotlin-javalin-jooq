package io.realworld.conduit.shared.infrastructure.router

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.delete
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.apibuilder.ApiBuilder.put
import io.javalin.core.security.SecurityUtil.roles
import io.realworld.conduit.article.infrastructure.api.ArticleListHandler
import io.realworld.conduit.profile.infrastructure.api.FollowProfileHandler
import io.realworld.conduit.profile.infrastructure.api.ProfileHandler
import io.realworld.conduit.profile.infrastructure.api.UnfollowProfileHandler
import io.realworld.conduit.user.infrastructure.api.AuthenticationAccessManager
import io.realworld.conduit.user.infrastructure.api.CurrentUserHandler
import io.realworld.conduit.user.infrastructure.api.Roles
import io.realworld.conduit.user.infrastructure.api.UpdateCurrentUserHandler
import io.realworld.conduit.user.infrastructure.api.UserSigninHandler
import io.realworld.conduit.user.infrastructure.api.UserSignupHandler
import org.koin.core.KoinComponent
import org.koin.core.inject

class Router : KoinComponent {

    private val authenticationAccessManager: AuthenticationAccessManager by inject()

    private val userSignupHandler: UserSignupHandler by inject()
    private val userSigninHandler: UserSigninHandler by inject()
    private val currentUserHandler: CurrentUserHandler by inject()
    private val updateCurrentUserHandler: UpdateCurrentUserHandler by inject()

    private val profileHandler: ProfileHandler by inject()
    private val followProfileHandler: FollowProfileHandler by inject()
    private val unFollowProfileHandler: UnfollowProfileHandler by inject()

    private val articleListHandler: ArticleListHandler by inject()

    fun setupRoutes(app: Javalin) {
        app.config.accessManager(authenticationAccessManager)
        app.routes {
            get("/articles", articleListHandler::handle, roles(Roles.AUTH))
            path("/users") {
                post(userSignupHandler::handle, roles(Roles.ANYONE))
                post("/login", userSigninHandler::handle, roles(Roles.ANYONE))
                get(currentUserHandler::handle, roles(Roles.AUTH))
                put(updateCurrentUserHandler::handle, roles(Roles.AUTH))
            }
            path("/profiles/:username") {
                get(profileHandler::handle, roles(Roles.ANYONE))
                post("/follow", followProfileHandler::handle, roles(Roles.AUTH))
                delete("/follow", unFollowProfileHandler::handle, roles(Roles.AUTH))
            }
        }
    }
}
