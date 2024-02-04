package com.goms.v2.domain.studentCouncil.dto.response

import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import java.time.LocalDate
import java.util.UUID

data class LateAccountHttpResponse(
    val accountIdx: UUID,
    val name: String,
    val grade: Int,
    val gender: Gender,
    val major: Major,
    val profileUrl: String?,
    val createdTime: LocalDate
)
