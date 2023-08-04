package com.goms.v2.domain.studentCouncil

import com.goms.v2.common.annotation.RootAggregate
import java.util.UUID

@RootAggregate
data class OutingUUID(
	val outingUUID: UUID,
	val expiredAt: Int
)
