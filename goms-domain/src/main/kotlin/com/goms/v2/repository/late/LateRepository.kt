package com.goms.v2.repository.late

import com.goms.v2.domain.late.Late
import java.time.LocalDate
import java.util.*

interface LateRepository {

    fun countByAccountIdx(accountIdx: UUID): Long
    fun saveAll(lateList: List<Late>)
    fun findTop3ByOrderByAccountDesc(): List<Late>
    fun countByOneWeekAgoLate(oneWeekAgo: LocalDate): Long
    fun findAllByCreatedTime(date: LocalDate): List<Late>?

}