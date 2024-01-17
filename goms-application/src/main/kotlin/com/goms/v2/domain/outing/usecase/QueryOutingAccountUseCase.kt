package com.goms.v2.domain.outing.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.outing.data.dto.OutingAccountDto
import com.goms.v2.repository.outing.OutingRepository

@UseCaseWithReadOnlyTransaction
class QueryOutingAccountUseCase(
    private val outingRepository: OutingRepository
) {

    fun execute(): List<OutingAccountDto> {
        val outing = outingRepository.findAllByOrderByCreatedTimeDesc()

        return outing.map {
            OutingAccountDto(
                accountIdx = it.account.idx,
                name = it.account.name,
                grade = it.account.grade,
                gender = it.account.gender,
                profileUrl = it.account.profileUrl,
                createdTime = it.createdTime
            )
        }
    }

}