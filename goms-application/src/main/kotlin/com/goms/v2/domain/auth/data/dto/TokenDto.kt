package com.goms.v2.domain.auth.data.dto

import com.goms.v2.domain.account.constant.Authority

data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExp: Int,
    val refreshTokenExp: Int,
    val authority: Authority
)
