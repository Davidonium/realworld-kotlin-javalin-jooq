package io.realworld.conduit.shared.infrastructure.persistence.spring

import org.jooq.Transaction
import org.jooq.TransactionContext
import org.jooq.TransactionProvider
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionDefinition.PROPAGATION_NESTED
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition


class SpringJooqTransactionProvider(private val transactionManager: PlatformTransactionManager) : TransactionProvider {
    override fun rollback(ctx: TransactionContext) {
        val tx = ctx.transaction() as SpringTransaction
        transactionManager.rollback(tx.transactionStatus)
    }

    override fun commit(ctx: TransactionContext) {
        val tx = ctx.transaction() as SpringTransaction
        transactionManager.commit(tx.transactionStatus)
    }

    override fun begin(ctx: TransactionContext) {
        val tx = transactionManager.getTransaction(DefaultTransactionDefinition(PROPAGATION_NESTED))
        ctx.transaction(SpringTransaction(tx))
    }
}

class SpringTransaction(val transactionStatus: TransactionStatus) : Transaction
