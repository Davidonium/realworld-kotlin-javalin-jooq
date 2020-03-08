package io.realworld.conduit.shared.domain


interface UnitOfWork {
    fun <R> transactional(block: () -> R): R
}
