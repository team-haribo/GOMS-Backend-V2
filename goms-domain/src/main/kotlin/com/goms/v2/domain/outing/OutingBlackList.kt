package com.goms.v2.domain.outing

import com.goms.v2.common.annotation.RootAggregate
import java.util.UUID

@RootAggregate
data class OutingBlackList(
    val accountIdx: UUID,
    val expiredAt: Int
)
