package com.goms.v2.domain.account.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.account.data.dto.ProfileDto
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.late.LateRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository

@UseCaseWithTransaction
class QueryAccountProfileUseCase(
    private val accountUtil: AccountUtil,
    private val lateRepository: LateRepository,
    private val outingRepository: OutingRepository,
    private val accountRepository: AccountRepository,
    private val outingBlackListRepository: OutingBlackListRepository
) {

    fun execute(): ProfileDto {
        val accountIdx = accountUtil.getCurrentAccountIdx()
        val account = accountRepository.findByIdOrNull(accountIdx) ?: throw AccountNotFoundException()
        val lateCount = lateRepository.countByAccountIdx(accountIdx)

        return ProfileDto(
            name = account.name,
            grade = account.grade,
            major = account.major,
            gender = account.gender,
            authority = account.authority,
            profileUrl = account.profileUrl,
            lateCount = lateCount,
            outing = outingRepository.existsByAccount(account),
            blackList = outingBlackListRepository.existsById(account.idx)
        )
    }

}