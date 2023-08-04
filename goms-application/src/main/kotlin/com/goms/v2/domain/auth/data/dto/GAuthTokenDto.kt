package com.goms.v2.domain.auth.data.dto

data class GAuthTokenDto(
    val accessToken: String,
    val refreshToken: String
)