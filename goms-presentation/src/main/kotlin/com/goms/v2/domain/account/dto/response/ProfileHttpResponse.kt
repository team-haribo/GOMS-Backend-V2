package com.goms.v2.domain.account.dto.response

import com.goms.v2.domain.account.Authority
import java.util.*

data class ProfileHttpResponse(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentNumHttpResponse,
    val authority: Authority,
    val profileUrl: String?,
    val lateCount: Long,
    val isOuting: Boolean,
    val isBlackList: Boolean
)