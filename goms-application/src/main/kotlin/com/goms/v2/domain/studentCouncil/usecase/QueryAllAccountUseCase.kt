package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository

@UseCaseWithReadOnlyTransaction
class QueryAllAccountUseCase(
    private val accountRepository: AccountRepository,
    private val outingBlackListRepository: OutingBlackListRepository
) {

    fun execute(): List<AccountDto> {
        val accountList = accountRepository.findAllOrderByStudentNum()
        val outingBlackListIdx = outingBlackListRepository.findAll().map { it.accountIdx }

        return accountList.map {
            AccountDto(
                accountIdx = it.idx,
                name = it.name,
                grade = it.grade,
                gender = it.gender,
                major = it.major,
                profileUrl = it.profileUrl,
                authority = it.authority,
                isBlackList = outingBlackListIdx.contains(it.idx)
            )
        }
    }

}
