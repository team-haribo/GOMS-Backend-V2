package com.goms.v2.domain.auth

import com.goms.v2.common.annotation.Aggregate
import java.time.LocalDateTime
import java.util.*

@Aggregate
data class RefreshToken(
    val refreshToken: String,
    val accountIdx: UUID,
    val expiredAt: LocalDateTime
)
