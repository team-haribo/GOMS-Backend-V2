package com.goms.v2.domain.studentCouncil.data.dto

import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import java.time.LocalDate
import java.util.*

data class LateAccountDto(
    val accountIdx: UUID,
    val name: String,
    val grade: Int,
    val gender: Gender,
    val major: Major,
    val profileUrl: String?,
    val createdTime: LocalDate
)
