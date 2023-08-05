package com.goms.v2.domain.email

import com.goms.v2.common.annotation.RootAggregate

@RootAggregate
data class EmailAuth(
    val email: String,
    var randomValue: String,
    var authentication: Boolean,
    var attemptCount: Int,
    val expiredAt: Int
) {

    fun updateAuthentication(authentication: Boolean) {
        this.authentication = authentication
    }

    fun updateRandomValue(randomValue: String) {
        this.randomValue = randomValue
    }

    fun increaseAttemptCount() {
        this.attemptCount += 1
    }

}