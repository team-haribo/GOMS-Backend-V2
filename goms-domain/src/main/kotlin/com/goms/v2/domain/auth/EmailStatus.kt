package com.goms.v2.domain.auth

import com.goms.v2.common.annotation.Aggregate

@Aggregate
enum class EmailStatus {
    BEFORE_SIGNUP, AFTER_SIGNUP
}