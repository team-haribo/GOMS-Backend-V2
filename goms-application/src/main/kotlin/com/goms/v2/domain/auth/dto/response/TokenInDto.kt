package com.goms.v2.domain.auth.dto.response

import com.goms.v2.domain.account.Authority
import java.time.LocalDateTime

data class TokenInDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExp: LocalDateTime,
    val refreshTokenExp: LocalDateTime,
    val authority: Authority
)
