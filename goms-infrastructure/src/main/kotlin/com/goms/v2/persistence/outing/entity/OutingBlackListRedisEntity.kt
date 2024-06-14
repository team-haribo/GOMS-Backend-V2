package com.goms.v2.persistence.outing.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.UUID
import java.util.concurrent.TimeUnit

@RedisHash("outing_black_list")
data class OutingBlackListRedisEntity(
    @Id
    val accountIdx: UUID,

    @TimeToLive(unit = TimeUnit.SECONDS)
    val expiredAt: Int
)
