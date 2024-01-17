package com.goms.v2.domain.studentCouncil.dto.response

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import java.util.*

data class AllAccountHttpResponse(
    val accountIdx: UUID,
    val name: String,
    val grade: Int,
    val gender: Gender,
    val profileUrl: String?,
    val authority: Authority,
    val isBlackList: Boolean
)