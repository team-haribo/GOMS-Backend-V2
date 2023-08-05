package com.goms.v2.persistence.email.entity

import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("email_auth")
data class EmailAuthRedisEntity(
    @Id
    val email: String,
    val authCode: String,
    val authentication: Boolean,
    @ColumnDefault("0")
    val attemptCount: Int,
    val expiredAt: Int
)