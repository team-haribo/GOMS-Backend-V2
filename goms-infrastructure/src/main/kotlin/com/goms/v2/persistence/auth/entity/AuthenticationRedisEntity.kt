package com.goms.v2.persistence.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import java.util.concurrent.TimeUnit

@RedisHash("authentication")
data class AuthenticationRedisEntity(
    @Id
    val email: String,
    val attemptCount: Int,
    val authCodeCount: Int,
    val isAuthentication: Boolean,
    @TimeToLive(unit = TimeUnit.SECONDS)
    val expiredAt: Int
)