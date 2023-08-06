package com.goms.v2.domain.email

import com.goms.v2.common.annotation.RootAggregate

@RootAggregate
data class AuthCode(
    val email: String,
    var authCode: String,
    val expiredAt: Int
) {

    fun updateAuthCode(authCode: String) {
        this.authCode = authCode
    }

}