package com.goms.v2.domain.outingDate.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.repository.outingDate.DeniedOutingDateRepository
import java.time.LocalDate

@UseCaseWithTransaction
class ValidateTodayOutingUseCase(
	private val deniedOutingDateRepository: DeniedOutingDateRepository
) {
	fun execute(): Boolean {
		val todayDate = LocalDate.now()
		return deniedOutingDateRepository.existsByOutingDate(todayDate)
	}
}
