package com.goms.v2.persistence.outing.repository

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.outing.Outing
import com.goms.v2.persistence.account.entity.QAccountJpaEntity.accountJpaEntity
import com.goms.v2.persistence.account.mapper.AccountMapper
import com.goms.v2.persistence.outing.entity.QOutingJpaEntity.outingJpaEntity
import com.goms.v2.persistence.outing.mapper.OutingMapper
import com.goms.v2.repository.outing.OutingRepository
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class OutingRepositoryImpl(
    private val outingJpaRepository: OutingJpaRepository,
    private val outingMapper: OutingMapper,
    private val accountMapper: AccountMapper,
    private val queryFactory: JPAQueryFactory
): OutingRepository {

    override fun save(outing: Outing) {
        val outingEntity = outingMapper.toEntity(outing)
        outingJpaRepository.save(outingEntity)
    }

    override fun deleteByAccountIdx(accountIdx: UUID) {
        outingJpaRepository.deleteByAccountIdx(accountIdx)
    }

    override fun deleteAllByAccountIdx(accountIdx: UUID) {
        outingJpaRepository.deleteAllByAccountIdx(accountIdx)
    }

    override fun existsByAccount(account: Account): Boolean {
        val accountEntity = accountMapper.toEntity(account)
        return outingJpaRepository.existsByAccount(accountEntity)
    }

    override fun findAllByOrderByCreatedTimeDesc(): List<Outing> =
        queryFactory.selectFrom(outingJpaEntity)
            .leftJoin(outingJpaEntity.account, accountJpaEntity).fetchJoin()
            .orderBy(outingJpaEntity.createdTime.desc())
            .fetch()
            .map { outingMapper.toDomain(it)!! }

    override fun count(): Long =
        outingJpaRepository.count()

    override fun findAll(): List<Outing> =
        queryFactory.selectFrom(outingJpaEntity)
            .leftJoin(outingJpaEntity.account, accountJpaEntity).fetchJoin()
            .fetch()
            .map { outingMapper.toDomain(it) }

    override fun findByAccountNameContaining(name: String?): List<Outing> =
        queryFactory
            .selectFrom(outingJpaEntity)
            .where(usernameEq(name))
            .fetch()
            .map { outingMapper.toDomain(it) }

    override fun deleteAll() {
        outingJpaRepository.deleteAll()
    }

    private fun usernameEq(name: String?): BooleanExpression? {
        if (name == null) return null
        return outingJpaEntity.account.name.containsIgnoreCase(name)
    }

}