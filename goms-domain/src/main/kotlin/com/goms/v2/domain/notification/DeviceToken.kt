package com.goms.v2.domain.notification

import com.goms.v2.common.annotation.RootAggregate
import java.util.UUID

@RootAggregate
data class DeviceToken(
    val accountIdx: UUID,
    val token: String
)