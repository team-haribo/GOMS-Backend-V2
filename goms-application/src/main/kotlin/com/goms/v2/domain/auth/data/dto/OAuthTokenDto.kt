package com.goms.v2.domain.auth.data.dto

data class OAuthTokenDto(
    val accessToken: String,
    val refreshToken: String
)