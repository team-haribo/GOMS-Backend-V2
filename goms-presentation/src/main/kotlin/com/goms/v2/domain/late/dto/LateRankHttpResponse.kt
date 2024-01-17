package com.goms.v2.domain.late.dto

import com.goms.v2.domain.account.constant.Gender
import java.util.*

data class LateRankHttpResponse(
    val accountIdx: UUID,
    val name: String,
    val grade: Int,
    val gender: Gender,
    val profileUrl: String?
)