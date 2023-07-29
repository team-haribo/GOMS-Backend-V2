package com.goms.v2.domain.auth.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.goms.v2.domain.account.Authority
import java.time.LocalDateTime

data class TokenWebResponse(
    val accessToken: String,
    val refreshToken: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
    val accessTokenExp: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH-mm-ss")
    val refreshTokenExp: LocalDateTime,
    val authority: Authority
)
