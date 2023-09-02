package com.goms.v2.repository.notification

import com.goms.v2.domain.notification.DeviceToken
import java.util.UUID

interface DeviceTokenRepository {

    fun save(deviceToken: DeviceToken)
    fun findAll(): List<DeviceToken>
    fun findByIdxOrNull(accountIdx: UUID): DeviceToken?

}