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
        val outingMap = outingRepository.findAllOutingAccountIdx()
            .associateWith { true }

        return accountRepository.findAccountByStudentInfo(dto.grade, dto.gender, dto.name, dto.authority, dto.major)
            .filter {
                when (dto.isBlackList) {
                    true -> outingBlackListIdx.contains(it.idx)
                    false -> !outingBlackListIdx.contains(it.idx)
                    else -> true  // isBlackList 쿼리파라미터가 없을 시 필터링 무조건 통과
                }
            }
            .filter {
                when (dto.isOuting){
                    true -> outingMap.contains(it.idx)
                    false -> !outingMap.contains(it.idx)
                    else -> true  // isOuting 쿼리파라미터가 없을 시 필터링 무조건 통과
                }
            }
            .map {
                AccountDto(
                    accountIdx = it.idx,
                    name = it.name,
                    grade = it.grade,
                    gender = it.gender,
                    major = it.major,
                    profileUrl = it.profileUrl,
                    authority = it.authority,
                    isBlackList = outingBlackListIdx.contains(it.idx),
                    outing = outingMap[it.idx] == true
                )
            }
    }
}