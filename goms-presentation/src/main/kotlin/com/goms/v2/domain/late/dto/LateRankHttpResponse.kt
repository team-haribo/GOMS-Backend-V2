package com.goms.v2.domain.late.dto

import com.goms.v2.domain.account.dto.response.StudentNumHttpResponse
import java.util.*

data class LateRankHttpResponse(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentNumHttpResponse,
    val profileUrl: String?
)