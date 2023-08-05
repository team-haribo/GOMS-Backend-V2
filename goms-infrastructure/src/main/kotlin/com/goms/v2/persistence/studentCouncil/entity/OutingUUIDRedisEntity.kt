package com.goms.v2.persistence.studentCouncil.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID
import java.util.concurrent.TimeUnit

@RedisHash("outing_uuid")
data class OutingUUIDRedisEntity(
    @Id
    val outingUUID: UUID,

    @TimeToLive(unit = TimeUnit.SECONDS)
    val expiredAt: Int
)
