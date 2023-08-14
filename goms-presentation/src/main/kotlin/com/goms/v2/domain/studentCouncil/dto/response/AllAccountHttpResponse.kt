package com.goms.v2.domain.studentCouncil.dto.response

import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.account.dto.response.StudentNumHttpResponse
import java.util.*

data class AllAccountHttpResponse(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentNumHttpResponse,
    val profileUrl: String?,
    val authority: Authority,
    val isBlackList: Boolean
)
