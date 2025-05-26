package com.goms.v2.domain.notification.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.auth.exception.AccountNotFoundException
import com.goms.v2.domain.notification.DeviceToken
import com.goms.v2.repository.account.AccountRepository
import com.goms.v2.repository.notification.DeviceTokenRepository
import mu.KotlinLogging

private val log = KotlinLogging.logger {  }

@UseCaseWithTransaction
class SetDeviceTokenUseCase(
    private val accountUtil: AccountUtil,
    private val accountRepository: AccountRepository,
    private val deviceTokenRepository: DeviceTokenRepository
) {

    fun execute(token: String) {
        val account = accountRepository.findByIdOrNull(accountUtil.getCurrentAccountIdx())
            ?: throw AccountNotFoundException()

        log.info("deviceToken is $token")

        deviceTokenRepository.save(
            DeviceToken(
                accountIdx = account.idx,
                token = setOf(token)
            )
        )
    }

}