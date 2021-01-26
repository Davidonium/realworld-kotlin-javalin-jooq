package io.realworld.conduit.shared.infrastructure.api

import io.javalin.Javalin
import io.realworld.conduit.shared.domain.ResourceNotFoundException
import org.eclipse.jetty.http.HttpStatus
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(ExceptionMapper::class.java)

class ExceptionMapper {
    fun map(app: Javalin) {
        app.exception(Exception::class.java) { e, ctx ->
            logger.error("Unhandled exception in request: ${ctx.method()} ${ctx.fullUrl()}", e)
            val error = HttpStatus.Code.INTERNAL_SERVER_ERROR
            ctx.status(error.code)
            ctx.json(
                ApiError(
                    status = error.code.toString(),
                    message = error.message
                )
            )
        }

        app.exception(ResourceNotFoundException::class.java) { _, ctx ->
            val error = HttpStatus.Code.NOT_FOUND
            ctx.status(error.code)
            ctx.json(
                ApiError(
                    status = error.code.toString(),
                    message = error.message
                )
            )
        }

        app.error(404) { ctx ->
            val error = HttpStatus.Code.NOT_FOUND
            ctx.status(error.code)
            ctx.json(
                ApiError(
                    status = error.code.toString(),
                    message = error.message
                )
            )
        }
    }
}

private data class ApiError(
    val status: String,
    val message: String
)
