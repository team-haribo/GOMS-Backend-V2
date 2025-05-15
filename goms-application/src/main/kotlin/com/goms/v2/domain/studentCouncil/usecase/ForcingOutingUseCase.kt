package com.goms.v2.domain.studentCouncil.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.outing.Outing
import com.goms.v2.domain.studentCouncil.exception.OutingAlreadyExistException
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository
import java.time.LocalTime
import java.util.UUID

@UseCaseWithTransaction
class ForcingOutingUseCase(
    private val outingRepository: OutingRepository,
    private val outingBlackListRepository: OutingBlackListRepository,
    private val accountRepository: AccountRepository
) {
    fun execute(outingUUID: UUID) {
        val account = accountRepository.findByIdOrNull(outingUUID) ?: throw AccountNotFoundException()
        if (outingBlackListRepository.existsById(account.idx)) {
            outingBlackListRepository.deleteById(account.idx)
        }
        when (outingRepository.existsByAccount(account)) {
            false -> outingRepository.save(
                Outing(
                    idx = -1,
                    account = account,
                    createdTime = LocalTime.now()
                )
            )

            true -> throw OutingAlreadyExistException()
        }
    }
}