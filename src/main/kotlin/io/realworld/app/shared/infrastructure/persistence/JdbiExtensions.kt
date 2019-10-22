package io.realworld.app.shared.infrastructure.persistence

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.withHandleUnchecked

/**
 * Wrapper extension method that will help migrate to another
 * because withHandleUnchecked is marked for future deprecation.
 */
inline fun <R> Jdbi.handled(crossinline block: (Handle) -> R): R {
    return withHandleUnchecked(block)
}