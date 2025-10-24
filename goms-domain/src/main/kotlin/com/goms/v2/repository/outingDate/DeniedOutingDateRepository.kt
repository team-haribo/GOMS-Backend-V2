package com.goms.v2.repository.outingDate

import com.goms.v2.domain.outingDate.DeniedOutingDate
import java.time.LocalDate

interface DeniedOutingDateRepository {
    fun existsByOutingDate(outingDate: LocalDate): Boolean

    fun findByOutingDate(outingDate: LocalDate): DeniedOutingDate

    fun deleteByOutingDate(outingDate: LocalDate)

    fun save(deniedOutingDate: DeniedOutingDate)
}
