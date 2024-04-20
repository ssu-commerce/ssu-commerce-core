package com.ssu.commerce.core.jpa.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import kotlin.properties.Delegates

abstract class QuerydslJPARepositorySupport(domainClass: Class<*>) : QuerydslRepositorySupport(domainClass) {

    protected var queryFactory: JPAQueryFactory by Delegates.notNull()

    @PersistenceContext
    override fun setEntityManager(entityManager: EntityManager) {
        this.queryFactory = JPAQueryFactory(entityManager)
        super.setEntityManager(entityManager)
    }
}
