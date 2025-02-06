package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.domain.studentCouncil.data.dto.SearchAccountDto
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository

@UseCaseWithReadOnlyTransaction
class SearchAccountUseCase(
    private val accountRepository: AccountRepository,
    private val outingRepository: OutingRepository,
    private val outingBlackListRepository: OutingBlackListRepository
) {

    fun execute(dto: SearchAccountDto): List<AccountDto> {
        val outingBlackListIdx = outingBlackListRepository.findAll().map { it.accountIdx }.toSet()
        val outingSet = outingRepository.findAllOutingAccountIdx().toSet()

        return accountRepository.findAccountByStudentInfo(dto.grade, dto.gender, dto.name, dto.authority, dto.major)
            .filter {
                when (dto.isBlackList) {
                    true -> it.idx in outingBlackListIdx
                    false -> it.idx !in outingBlackListIdx
                    else -> true
                }
            }
            .filter {
                when (dto.isOuting) {
                    true -> it.idx in outingSet
                    false -> it.idx !in outingSet
                    else -> true
                }
            }
            .map {
                AccountDto(
                    accountIdx = it.idx,
                    name = it.name,
                    grade = it.grade,
                    gender = it.gender,
                    major = it.major,
                    profileUrl = it.profileUrl ?: null,
                    authority = it.authority,
                    isBlackList = it.idx in outingBlackListIdx,
                    outing = it.idx in outingSet
                )
            }
    }
}