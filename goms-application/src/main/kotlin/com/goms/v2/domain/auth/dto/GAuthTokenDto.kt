package com.goms.v2.domain.auth.dto

data class GAuthTokenDto(
    val accessToken: String,
    val refreshToken: String
)