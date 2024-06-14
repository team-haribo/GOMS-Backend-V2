package com.goms.v2.domain.studentCouncil.dto.request

import com.goms.v2.domain.account.constant.Authority
import org.jetbrains.annotations.NotNull
import java.util.UUID

data class GrantAuthorityHttpRequest(
    @field:NotNull
    val accountIdx: UUID,
    @field:NotNull
    val authority: Authority
)