package com.goms.v2.repository.outingDate

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.outing.DeniedOutingDate
import com.goms.v2.domain.outing.Outing
import java.time.LocalDate
import java.util.*

interface DeniedOutingDateRepository {
	fun existsByOutingDate(outingDate: LocalDate): Boolean

	fun findByOutingDate(outingDate: LocalDate): DeniedOutingDate

	fun deleteByOutingDate(outingDate: LocalDate)

	fun save(deniedOutingDate: DeniedOutingDate)
}
