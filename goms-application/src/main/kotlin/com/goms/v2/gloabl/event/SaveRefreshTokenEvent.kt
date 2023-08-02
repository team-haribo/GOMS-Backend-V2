package com.goms.v2.gloabl.event

import java.time.LocalDateTime
import java.util.*

data class SaveRefreshTokenEvent(
    val refreshToken: String,
    val accountIdx: UUID,
    val expiredAt: LocalDateTime
)
