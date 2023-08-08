package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import java.util.UUID

@UseCaseWithTransaction
class DeleteOutingBlacklistUseCase(
    private val accountRepository: AccountRepository,
    private val outingBlackListRepository: OutingBlackListRepository
) {

    fun execute(accountIdx: UUID) {
        val account = accountRepository.findByIdOrNull(accountIdx) ?: throw AccountNotFoundException()
        outingBlackListRepository.deleteById(account.idx)
    }

}