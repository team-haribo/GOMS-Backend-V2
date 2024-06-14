package com.goms.v2.domain.late.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.late.data.dto.LateRankDto
import com.goms.v2.repository.late.LateRepository

@UseCaseWithReadOnlyTransaction
class QueryLateRankUseCase(
    private val lateRepository: LateRepository,
) {

    fun execute(): List<LateRankDto> {
        val lateRankList = lateRepository.findTop3ByOrderByAccountDesc()
        return lateRankList.map {
            LateRankDto(
                accountIdx = it.account.idx,
                name = it.account.name,
                grade = it.account.grade,
                major = it.account.major,
                gender = it.account.gender,
                profileUrl = it.account.profileUrl
            )
        }
    }

}