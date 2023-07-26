package com.goms.v2.domain.account

import com.goms.v2.common.annotation.Aggregate

@Aggregate
enum class Authority {

    ROLE_ACCOUNT, ROLE_ADMIN

}