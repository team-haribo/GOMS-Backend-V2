package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.outing.OutingBlackList
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import java.util.UUID

@UseCaseWithTransaction
class SaveOutingBlackListUseCase(
    private val accountRepository: AccountRepository,
    private val outingBlacklistRepository: OutingBlackListRepository
) {

    fun execute(accountIdx: UUID) {
        val account = accountRepository.findByIdOrNull(accountIdx) ?: throw AccountNotFoundException()
        val outingBlackList = OutingBlackList(
            accountIdx = account.idx,
            expiredAt = 604800
        )
        outingBlacklistRepository.save(outingBlackList)
    }

}