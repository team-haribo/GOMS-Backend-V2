package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingRepository
import java.util.*

@UseCaseWithTransaction
class DeleteOutingUseCase(
    private val accountRepository: AccountRepository,
    private val outingRepository: OutingRepository,
) {

    fun execute(accountIdx: UUID) {
        val account = accountRepository.findByIdOrNull(accountIdx) ?: throw AccountNotFoundException()
        outingRepository.deleteByAccountIdx(account.idx)
    }

}