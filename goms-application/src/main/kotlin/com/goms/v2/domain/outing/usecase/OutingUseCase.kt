package com.goms.v2.domain.outing.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.outing.Outing
import com.goms.v2.domain.outing.exception.BlackListNotAllowOutingException
import com.goms.v2.domain.outing.exception.OutingUUIDUnverifiedException
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository
import com.goms.v2.repository.studentCouncil.OutingUUIDRepository
import java.time.LocalTime
import java.util.UUID

@UseCaseWithTransaction
class OutingUseCase(
    private val outingRepository: OutingRepository,
    private val outingBlackListRepository: OutingBlackListRepository,
    private val outingUUIDRepository: OutingUUIDRepository,
    private val accountUtil: AccountUtil
) {

    fun execute(outingUUID: UUID) {
        val account = accountUtil.getCurrentAccount()

        if (outingBlackListRepository.existsById(account.idx)) {
            throw BlackListNotAllowOutingException()
        }

        if (!outingUUIDRepository.existsById(outingUUID)) {
            throw OutingUUIDUnverifiedException()
        }

        when (outingRepository.existsByAccount(account)) {
            false -> outingRepository.save(Outing(
                idx = -1,
                account = account,
                createdTime = LocalTime.now()))
            true -> outingRepository.deleteByAccountIdx(account.idx)
        }
    }

}