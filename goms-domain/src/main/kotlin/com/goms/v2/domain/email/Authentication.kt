package com.goms.v2.domain.email

import com.goms.v2.common.annotation.RootAggregate

@RootAggregate
data class Authentication(
    val email: String,
    var attemptCount: Int,
    var isAuthentication: Boolean,
    val expiredAt: Int
) {

    fun updateAuthentication(isAuthentication: Boolean) {
        this.isAuthentication = isAuthentication
    }

    fun increaseAttemptCount() {
        this.attemptCount += 1
    }

}