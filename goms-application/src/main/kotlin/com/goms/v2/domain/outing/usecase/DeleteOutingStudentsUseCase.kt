package com.goms.v2.domain.outing.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.repository.outing.OutingRepository

@UseCaseWithTransaction
class DeleteOutingStudentsUseCase(
    private val outingRepository: OutingRepository
) {

    fun execute() = outingRepository.deleteAll()

}