package com.goms.v2.persistence.account.repository

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import com.goms.v2.persistence.account.entity.QAccountJpaEntity.accountJpaEntity
import com.goms.v2.persistence.account.mapper.AccountMapper
import com.goms.v2.repository.account.AccountRepository
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class AccountRepositoryImpl(
    private val accountJpaRepository: AccountJpaRepository,
    private val accountMapper: AccountMapper,
    private val queryFactory: JPAQueryFactory
): AccountRepository {

    override fun save(account: Account): Account {
        val accountEntity = accountMapper.toEntity(account)
        accountJpaRepository.save(accountEntity)
        return account
    }

    override fun findByIdOrNull(accountIdx: UUID) =
        accountJpaRepository.findByIdOrNull(accountIdx)
            .let { accountMapper.toDomain(it) }

    override fun findByEmail(email: String) =
        accountJpaRepository.findByEmail(email)
            .let { accountMapper.toDomain(it) }

    override fun existsByEmail(email: String): Boolean =
        accountJpaRepository.existsByEmail(email)

    override fun findAll() =
        accountJpaRepository.findAll()
            .map { accountMapper.toDomain(it)!! }

    override fun findAllOrderByStudentNum(): List<Account> =
        queryFactory.selectFrom(accountJpaEntity)
            .orderBy(accountJpaEntity.gender.asc())
            .fetch()
            .map { accountMapper.toDomain(it)!! }

    override fun findAccountByStudentInfo(
        grade: Int?,
        gender: Gender?,
        name: String?,
        authority: Authority?,
        major: Major?
    ): List<Account> =
        queryFactory
            .selectFrom(accountJpaEntity)
            .where(
                eqGrade(grade),
                eqGender(gender),
                eqMajor(major),
                likeName(name),
                eqAuthority(authority)
            )
            .orderBy(accountJpaEntity.grade.asc())
            .fetch()
            .map { accountMapper.toDomain(it)!! }

    private fun eqGrade(grade: Int?): BooleanExpression? {
        if (grade == null) return null
        return accountJpaEntity.grade.eq(grade)
    }

    private fun likeName(name: String?): BooleanExpression? {
        if (name.isNullOrEmpty()) return null
        return accountJpaEntity.name.like("%$name%")
    }

    private fun eqAuthority(authority: Authority?): BooleanExpression? {
        if (authority == null) return null
        return accountJpaEntity.authority.eq(authority)
    }

    private fun eqGender(gender: Gender?): BooleanExpression? {
        if (gender == null) return null
        return accountJpaEntity.gender.eq(gender)
    }

    private fun eqMajor(major: Major?): BooleanExpression? {
        if (major == null) return null
        return accountJpaEntity.major.eq(major)
    }

}
