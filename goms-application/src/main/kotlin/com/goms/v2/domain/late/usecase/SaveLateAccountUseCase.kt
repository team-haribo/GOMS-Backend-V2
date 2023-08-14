package com.goms.v2.domain.late.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.domain.late.Late
import com.goms.v2.domain.outing.OutingBlackList
import com.goms.v2.repository.late.LateRepository
import com.goms.v2.repository.outing.OutingBlackListRepository
import com.goms.v2.repository.outing.OutingRepository
import mu.KotlinLogging
import java.time.Duration
import java.time.LocalDate

private val log = KotlinLogging.logger {}

@UseCaseWithTransaction
class SaveLateAccountUseCase(
    private val outingRepository: OutingRepository,
    private val lateRepository: LateRepository,
    private val outingBlackListRepository: OutingBlackListRepository
) {

    fun execute() {
        val outingList = outingRepository.findAll().toList()
        val lateList = ArrayList<Late>(outingList.size)
        val outingBlackList = ArrayList<OutingBlackList>(outingList.size)

        outingList.forEach {
            lateList.add(
                Late(
                    idx = -1,
                    account = it.account,
                    createdTime = LocalDate.now()
                )
            )
            outingBlackList.add(
                OutingBlackList(
                    accountIdx = it.account.idx,
                    expiredAt = Duration.ofDays(7).toSeconds().toInt()
                )
            )
        }

        log.info { "late outing student count is: ${outingList.size}" }

        lateRepository.saveAll(lateList)
        outingBlackListRepository.saveAll(outingBlackList)
    }

}