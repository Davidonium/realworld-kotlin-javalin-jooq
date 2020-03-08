package io.realworld.conduit.shared.infrastructure.persistence.spring

import io.realworld.conduit.shared.domain.UnitOfWork
import org.springframework.transaction.support.TransactionTemplate

class SpringUnitOfWork(private val tx: TransactionTemplate) : UnitOfWork {
    override fun <R> transactional(block: () -> R): R = tx.execute { block() } as R
}
