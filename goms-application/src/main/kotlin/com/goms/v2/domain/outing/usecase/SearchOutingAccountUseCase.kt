package com.goms.v2.domain.outing.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.outing.data.dto.OutingAccountDto
import com.goms.v2.repository.outing.OutingRepository

@UseCaseWithTransaction
class SearchOutingAccountUseCase(
    private val outingRepository: OutingRepository
) {

    fun execute(name: String?): List<OutingAccountDto> =
        outingRepository.findByAccountNameContaining(name)
            .map {
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