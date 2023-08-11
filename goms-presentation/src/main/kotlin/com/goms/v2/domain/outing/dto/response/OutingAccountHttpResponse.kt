package com.goms.v2.domain.outing.dto.response

import com.goms.v2.domain.account.dto.response.StudentNumHttpResponse
import java.util.*

data class OutingAccountHttpResponse(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentNumHttpResponse,
    val profileUrl: String?,
    val createdTime: String
)