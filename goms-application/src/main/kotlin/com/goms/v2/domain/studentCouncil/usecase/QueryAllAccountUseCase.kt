package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.account.data.dto.StudentNumberDto
import com.goms.v2.domain.studentCouncil.data.dto.AllAccountDto
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository

@UseCaseWithReadOnlyTransaction
class QueryAllAccountUseCase(
    private val accountRepository: AccountRepository,
    private val outingBlackListRepository: OutingBlackListRepository
) {

    fun execute(): List<AllAccountDto> {
        val accountList = accountRepository.findAllOrderByStudentNum()
        val outingBlackListIdx = outingBlackListRepository.findAll().map { it.accountIdx }

        return accountList.map {
            AllAccountDto(
                accountIdx = it.idx,
                name = it.name,
                studentNum = StudentNumberDto(it.studentNumber.grade, it.studentNumber.classNum, it.studentNumber.number),
                profileUrl = it.profileUrl,
                authority = it.authority,
                isBlackList = outingBlackListIdx.contains(it.idx)
            )
        }
    }

}