package com.goms.v2.domain.auth

import com.goms.v2.common.annotation.RootAggregate
import java.util.*

@RootAggregate
data class RefreshToken(
    val refreshToken: String,
    val accountIdx: UUID,
    val expiredAt: Int
)
