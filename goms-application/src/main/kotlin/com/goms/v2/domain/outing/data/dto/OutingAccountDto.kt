package com.goms.v2.domain.outing.data.dto

import com.goms.v2.domain.account.data.dto.StudentNumberDto
import java.time.LocalTime
import java.util.*

data class OutingAccountDto(
    val accountIdx: UUID,
    val name: String,
    val studentNum: StudentNumberDto,
    val profileUrl: String?,
    val createdTime: LocalTime
)