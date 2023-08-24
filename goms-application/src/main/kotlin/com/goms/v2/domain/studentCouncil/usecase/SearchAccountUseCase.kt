package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.account.data.dto.StudentNumberDto
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import kotlin.streams.asSequence

@UseCaseWithReadOnlyTransaction
class SearchAccountUseCase(
    private val accountRepository: AccountRepository,
    private val outingBlackListRepository: OutingBlackListRepository
) {

    fun execute(grade: Int?, classNum: Int?, name: String?, authority: Authority?, isBlackList: Boolean?): List<AccountDto> {
        val outingBlackLIstIdx = outingBlackListRepository.findAll().map { it.accountIdx }

        return accountRepository.findAccountByStudentInfo(grade, classNum, name, authority).stream().asSequence()
            .filter {
                if (isBlackList != null && isBlackList) outingBlackLIstIdx.contains(it.idx)
                else if (isBlackList != null) outingBlackLIstIdx.contains(it.idx).not()
                else true
            }.map {
                AccountDto(
                    accountIdx = it.idx,
                    name = it.name,
                    studentNum = StudentNumberDto(it.studentNumber.grade, it.studentNumber.classNum, it.studentNumber.number),
                    profileUrl = it.profileUrl,
                    authority = it.authority,
                    isBlackList = outingBlackLIstIdx.contains(it.idx)
                )
            }.toList()
    }

}