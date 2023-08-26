package com.goms.v2.persistence.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.concurrent.TimeUnit

@RedisHash("auth_code")
data class AuthCodeRedisEntity(
    @Id
    val email: String,
    val authCode: String,
    @TimeToLive(unit = TimeUnit.SECONDS)
    val expiredAt: Int
)