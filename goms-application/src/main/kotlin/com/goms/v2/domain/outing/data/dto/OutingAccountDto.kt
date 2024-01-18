package com.goms.v2.domain.outing.data.dto

import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import java.time.LocalTime
import java.util.*

data class OutingAccountDto(
    val accountIdx: UUID,
    val name: String,
    val grade: Int,
    val major: Major,
    val gender: Gender,
    val profileUrl: String?,
    val createdTime: LocalTime
)