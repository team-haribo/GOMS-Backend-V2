package com.goms.v2.domain.outingDate

import com.goms.v2.common.annotation.RootAggregate
import java.time.LocalDate

@RootAggregate
data class DeniedOutingDate(
    val idx: Long,
    val outingDate: LocalDate,
)
