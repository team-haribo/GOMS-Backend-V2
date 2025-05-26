package com.goms.v2.persistence.notification.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import java.util.*

@RedisHash("device_token")
data class DeviceTokenEntity(
    @Id
    val accountIdx: UUID,
    val token: Set<String>
)