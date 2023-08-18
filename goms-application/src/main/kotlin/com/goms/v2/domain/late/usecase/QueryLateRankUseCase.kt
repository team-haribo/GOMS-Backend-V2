package com.goms.v2.domain.late.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.account.data.dto.StudentNumberDto
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
                studentNum = StudentNumberDto(
                    grade = it.account.studentNumber.grade,
                    classNum = it.account.studentNumber.classNum,
                    number = it.account.studentNumber.number
                ),
                profileUrl = it.account.profileUrl
            )
        }
    }

}