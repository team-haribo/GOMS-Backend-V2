package com.goms.v2.persistence.late.repository

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.StudentNumber
import com.goms.v2.domain.late.Late
import com.goms.v2.persistence.late.entity.QLateJpaEntity.lateJpaEntity
import com.goms.v2.persistence.late.mapper.LateMapper
import com.goms.v2.repository.late.LateRepository
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
class LateRepositoryImpl(
    private val lateJpaRepository: LateJpaRepository,
    private val lateMapper: LateMapper,
    private val jpaQueryFactory: JPAQueryFactory
): LateRepository {

    override fun countByAccountIdx(accountIdx: UUID): Long =
        lateJpaRepository.countByAccountIdx(accountIdx)

    override fun saveAll(lateList: List<Late>) {
        lateList
            .map { lateMapper.toEntity(it) }
            .let { lateJpaRepository.saveAll(it) }
    }

    override fun findTop3ByOrderByAccountDesc(): List<Late> =
        jpaQueryFactory.from(lateJpaEntity)
            .select(
                Projections.constructor(
                    Late::class.java,
                    lateJpaEntity.idx,
                    Projections.constructor(
                        Account::class.java,
                        lateJpaEntity.account.idx,
                        lateJpaEntity.account.email,
                        Projections.constructor(
                            StudentNumber::class.java,
                            lateJpaEntity.account.studentNumber.grade,
                            lateJpaEntity.account.studentNumber.classNum,
                            lateJpaEntity.account.studentNumber.number
                        ),
                        lateJpaEntity.account.name,
                        lateJpaEntity.account.profileUrl,
                        lateJpaEntity.account.authority,
                        lateJpaEntity.account.createdTime
                    ),
                    lateJpaEntity.createdTime
                )
            )
            .groupBy(lateJpaEntity.account.idx)
            .orderBy(lateJpaEntity.account.idx.count().desc())
            .limit(3)
            .fetch()

    override fun countByOneWeekAgoLate(oneWeekAgo: LocalDate): Long =
        lateJpaRepository.countByOneWeekAgoLate(oneWeekAgo)

}