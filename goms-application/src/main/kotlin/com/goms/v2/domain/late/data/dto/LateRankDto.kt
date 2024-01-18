package com.goms.v2.domain.late.data.dto

import com.goms.v2.domain.account.constant.Gender
import java.util.*

data class LateRankDto(
    val accountIdx: UUID,
    val name: String,
    val grade: Int,
    val gender: Gender,
    val profileUrl: String?
)