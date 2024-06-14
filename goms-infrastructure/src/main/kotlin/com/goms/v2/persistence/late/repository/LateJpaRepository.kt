package com.goms.v2.persistence.late.repository

import com.goms.v2.persistence.late.entity.LateJpaEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate
import java.util.UUID

interface LateJpaRepository: CrudRepository<LateJpaEntity, UUID> {

    fun countByAccountIdx(accountIdx: UUID): Long
    @Query("select count(*) from late l where l.createdTime = :oneWeekAgo")
    fun countByOneWeekAgoLate(oneWeekAgo: LocalDate): Long
    fun findAllByCreatedTime(date: LocalDate): List<LateJpaEntity>
    fun deleteAllByAccountIdx(accountIdx: UUID)
}