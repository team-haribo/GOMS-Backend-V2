package com.goms.v2.domain.auth

import com.goms.v2.common.annotation.RootAggregate

@RootAggregate
data class AuthCode(
    val email: String,
    var authCode: String,
    val expiredAt: Long
)