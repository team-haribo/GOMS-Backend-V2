package com.goms.v2.domain.auth

import com.goms.v2.common.annotation.RootAggregate

@RootAggregate
data class Authentication(
    val email: String,
    var attemptCount: Int,
    var authCodeCount: Int,
    var isAuthentication: Boolean,
    val expiredAt: Long
) {

    companion object {
        const val EXPIRED_AT = 300L
    }

    fun certified() =
        this.copy(
            isAuthentication = true
        )

    fun increaseAuthCodeCount() =
        this.copy(
            authCodeCount = authCodeCount.inc()
        )

    fun increaseAttemptCount() =
        this.copy(
            attemptCount = attemptCount.inc()
        )


}