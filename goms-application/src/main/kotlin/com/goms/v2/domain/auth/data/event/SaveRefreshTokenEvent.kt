package com.goms.v2.domain.auth.data.event

import java.util.*

data class SaveRefreshTokenEvent(
    val refreshToken: String,
    val accountIdx: UUID,
    val expiredAt: Int
)
