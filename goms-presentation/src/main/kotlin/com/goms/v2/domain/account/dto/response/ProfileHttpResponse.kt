package com.goms.v2.domain.account.dto.response

import com.goms.v2.domain.account.Authority

data class ProfileHttpResponse(
    val name: String,
    val studentNum: StudentNumHttpResponse,
    val authority: Authority,
    val profileUrl: String?,
    val lateCount: Long,
    val isOuting: Boolean,
    val isBlackList: Boolean
)