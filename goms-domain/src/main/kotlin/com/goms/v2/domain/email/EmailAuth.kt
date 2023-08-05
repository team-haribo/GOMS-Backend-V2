package com.goms.v2.domain.email

import com.goms.v2.common.annotation.RootAggregate

@RootAggregate
data class EmailAuth(
    val email: String,
    var authCode: String,
    var isAuthentication: Boolean,
    var attemptCount: Int,
    val expiredAt: Int
) {

    fun updateAuthentication(isAuthentication: Boolean) {
        this.isAuthentication = isAuthentication
    }

    fun updateAuthKey(authCode: String) {
        this.authCode = authCode
    }

    fun increaseAttemptCount() {
        this.attemptCount += 1
    }

}