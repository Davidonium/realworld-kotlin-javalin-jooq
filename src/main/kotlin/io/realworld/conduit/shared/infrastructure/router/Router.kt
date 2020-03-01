package io.realworld.conduit.shared.infrastructure.router

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.delete
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.apibuilder.ApiBuilder.put
import io.javalin.core.security.SecurityUtil.roles
import io.realworld.conduit.article.infrastructure.api.CreateArticleHandler
import io.realworld.conduit.article.infrastructure.api.RecentArticlesHandler
import io.realworld.conduit.profile.infrastructure.api.FollowProfileHandler
import io.realworld.conduit.profile.infrastructure.api.ProfileHandler
import io.realworld.conduit.profile.infrastructure.api.UnfollowProfileHandler
import io.realworld.conduit.user.infrastructure.api.CurrentUserHandler
import io.realworld.conduit.user.infrastructure.api.Roles
import io.realworld.conduit.user.infrastructure.api.TokenAccessManager
import io.realworld.conduit.user.infrastructure.api.UpdateCurrentUserHandler
import io.realworld.conduit.user.infrastructure.api.UserSigninHandler
import io.realworld.conduit.user.infrastructure.api.UserSignupHandler

class Router(
    private val authenticationAccessManager: TokenAccessManager,

    private val userSignupHandler: UserSignupHandler,
    private val userSigninHandler: UserSigninHandler,
    private val currentUserHandler: CurrentUserHandler,
    private val updateCurrentUserHandler: UpdateCurrentUserHandler,

    private val profileHandler: ProfileHandler,
    private val followProfileHandler: FollowProfileHandler,
    private val unFollowProfileHandler: UnfollowProfileHandler,

    private val recentArticlesHandler: RecentArticlesHandler,
    private val createArticleHandler: CreateArticleHandler
) {
    fun setupRoutes(app: Javalin) {
        app.config.accessManager(authenticationAccessManager)
        app.routes {
            get("/articles", recentArticlesHandler::handle, roles(Roles.AUTH))
            post("/articles", createArticleHandler::handle, roles(Roles.AUTH))
            get("/user", currentUserHandler::handle, roles(Roles.AUTH))
            path("/users") {
                post(userSignupHandler::handle, roles(Roles.ANYONE))
                post("/login", userSigninHandler::handle, roles(Roles.ANYONE))
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
