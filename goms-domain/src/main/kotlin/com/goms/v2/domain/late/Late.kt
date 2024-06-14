package com.goms.v2.domain.late

import com.goms.v2.common.annotation.RootAggregate
import com.goms.v2.domain.account.Account
import java.time.LocalDate

@RootAggregate
data class Late(
    val idx: Long,
    val account: Account,
    val createdTime: LocalDate
)