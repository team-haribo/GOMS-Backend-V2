package com.goms.v2.domain.late.dto

import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import java.util.*

data class LateRankHttpResponse(
    val accountIdx: UUID,
    val name: String,
    val grade: Int,
    val major: Major,
    val gender: Gender,
    val profileUrl: String?
)