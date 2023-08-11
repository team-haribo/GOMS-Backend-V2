package com.goms.v2.domain.outing.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.repository.outing.OutingRepository

@UseCaseWithReadOnlyTransaction
class QueryOutingCountUseCase(
    private val outingRepository: OutingRepository
) {

    fun execute(): Long = outingRepository.count()

}