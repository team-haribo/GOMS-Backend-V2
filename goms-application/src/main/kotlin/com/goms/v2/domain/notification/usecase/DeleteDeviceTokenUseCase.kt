package com.goms.v2.domain.notification.usecase

import com.goms.v2.common.annotation.UseCaseWithTransaction
import com.goms.v2.common.util.AccountUtil
import com.goms.v2.domain.notification.exception.DeviceTokenNotFoundException
import com.goms.v2.repository.notification.DeviceTokenRepository

@UseCaseWithTransaction
class DeleteDeviceTokenUseCase(
    private val deviceTokenRepository: DeviceTokenRepository,
    private val accountUtil: AccountUtil
) {

    fun execute() {
        val accountIdx = accountUtil.getCurrentAccountIdx()
        val deviceToken = deviceTokenRepository.findByIdxOrNull(accountIdx)
            ?: throw DeviceTokenNotFoundException()
        deviceTokenRepository.delete(deviceToken)
    }

}