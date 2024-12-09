package com.goms.v2.persistence.outingDate.repository

import com.goms.v2.persistence.outingDate.entity.DeniedOutingDateJpaEntity
import org.springframework.data.repository.CrudRepository
import java.time.LocalDate

interface DeniedOutingDateJpaRepository: CrudRepository<DeniedOutingDateJpaEntity, Long> {
	fun existsByOutingDate(outingDate: LocalDate): Boolean

	fun findByOutingDate(outingDate: LocalDate): DeniedOutingDateJpaEntity

	fun deleteByOutingDate(outingDate: LocalDate)
}
