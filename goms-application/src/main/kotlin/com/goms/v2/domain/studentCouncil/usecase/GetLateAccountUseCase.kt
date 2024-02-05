package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.late.exception.LateAccountNotFound
import com.goms.v2.domain.studentCouncil.data.dto.LateAccountDto
import com.goms.v2.repository.late.LateRepository
import java.time.LocalDate

@UseCaseWithReadOnlyTransaction
class GetLateAccountUseCase(
    private val lateRepository: LateRepository
) {
    fun execute(date: LocalDate): List<LateAccountDto>? {
        val lateAccounts = lateRepository.findAllByCreatedTime(date) ?: throw LateAccountNotFound()

        return lateAccounts.map {
            LateAccountDto(
                accountIdx = it.account.idx,
                name = it.account.name,
                grade = it.account.grade,
                gender = it.account.gender,
                major = it.account.major,
                profileUrl = it.account.profileUrl,
            )
        }
    }


}