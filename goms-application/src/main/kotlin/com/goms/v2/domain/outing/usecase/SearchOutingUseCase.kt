package com.goms.v2.domain.outing.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.account.data.dto.StudentNumberDto
import com.goms.v2.domain.outing.data.dto.OutingAccountDto
import com.goms.v2.repository.outing.OutingRepository

@UseCaseWithReadOnlyTransaction
class SearchOutingUseCase(
    private val outingRepository: OutingRepository
) {

    fun execute(name: String?): List<OutingAccountDto> {
        if (name == null) {
            return outingRepository.findAll()
                .map {
                    OutingAccountDto(
                        accountIdx = it.account.idx,
                        name = it.account.name,
                        studentNum = StudentNumberDto(
                            it.account.studentNumber.grade,
                            it.account.studentNumber.classNum,
                            it.account.studentNumber.number
                        ),
                        profileUrl = it.account.profileUrl,
                        createdTime = it.createdTime
                    )
                }
        }

        return outingRepository.queryByAccountNameContaining(name)
            .map {
                OutingAccountDto(
                    accountIdx = it.account.idx,
                    name = it.account.name,
                    studentNum = StudentNumberDto(
                        it.account.studentNumber.grade,
                        it.account.studentNumber.classNum,
                        it.account.studentNumber.number
                    ),
                    profileUrl = it.account.profileUrl,
                    createdTime = it.createdTime
                )
            }
    }

}