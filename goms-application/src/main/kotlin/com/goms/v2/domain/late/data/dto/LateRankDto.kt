package com.goms.v2.domain.late.data.dto

import com.goms.v2.domain.account.data.dto.StudentNumberDto
import java.util.*

data class LateRankDto(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentNumberDto,
    val profileUrl: String?
)