package com.goms.v2.domain.outing

import com.goms.v2.common.annotation.RootAggregate
import com.goms.v2.domain.account.Account
import java.time.LocalTime

@RootAggregate
data class Outing(
    val idx: Long,
    val account: Account,
    val createdTime: LocalTime
)
