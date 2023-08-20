package com.goms.v2.domain.studentCouncil.data.dto

import com.goms.v2.domain.account.Authority
import java.util.UUID

data class GrantAuthorityDto(
    val accountIdx: UUID,
    val authority: Authority
)
