package com.goms.v2.persistence.outingDate.repository

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.outing.DeniedOutingDate
import com.goms.v2.domain.outing.Outing
import com.goms.v2.persistence.outing.mapper.DeniedOutingDateMapper
import com.goms.v2.repository.outingDate.DeniedOutingDateRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

@Repository
class DeniedOutingDateRepositoryImpl (
	private val deniedOutingDateJpaRepository: DeniedOutingDateJpaRepository,
	private val deniedOutingDateMapper: DeniedOutingDateMapper
): DeniedOutingDateRepository {
	override fun existsByOutingDate(outingDate: LocalDate): Boolean =
		deniedOutingDateJpaRepository.existsByOutingDate(outingDate)

	override fun findByOutingDate(outingDate: LocalDate): DeniedOutingDate {
		val deniedOutingDateEntity = deniedOutingDateJpaRepository.findByOutingDate(outingDate)
		return deniedOutingDateMapper.toDomain(deniedOutingDateEntity)
	}

	override fun deleteByOutingDate(outingDate: LocalDate) {
		deniedOutingDateJpaRepository.deleteByOutingDate(outingDate)
	}

	override fun save(deniedOutingDate: DeniedOutingDate){
		val deniedOutingDateEntity = deniedOutingDateMapper.toEntity(deniedOutingDate)
		deniedOutingDateJpaRepository.save(deniedOutingDateEntity)
	}
}