package io.realworld.conduit.shared.infrastructure.router

import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.core.security.SecurityUtil.roles
import io.realworld.conduit.article.infrastructure.api.ArticleListHandler
import io.realworld.conduit.user.infrstructure.api.AuthenticationAccessManager
import io.realworld.conduit.user.infrstructure.api.Roles
import io.realworld.conduit.user.infrstructure.api.UserSigninHandler
import io.realworld.conduit.user.infrstructure.api.UserSignupHandler

class Router(
    private val articleListHandler: ArticleListHandler,
    private val userSignupHandler: UserSignupHandler,
    private val userSigninHandler: UserSigninHandler,
    private val authenticationAccessManager: AuthenticationAccessManager
) {

    fun setupRoutes(app: Javalin) {
        app.routes {
            get("/articles", articleListHandler::handle, roles(Roles.AUTH))
            path("/users") {
                post(userSignupHandler::handle, roles(Roles.ANYONE))
                post("/login", userSigninHandler::handle, roles(Roles.ANYONE))
            }
        }
        app.config.accessManager(authenticationAccessManager)
    }
}
