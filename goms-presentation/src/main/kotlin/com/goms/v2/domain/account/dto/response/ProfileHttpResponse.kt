package com.goms.v2.domain.account.dto.response

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major

data class ProfileHttpResponse(
    val name: String,
    val grade: Int,
    val gender: Gender,
    val major: Major,
    val authority: Authority,
    val profileUrl: String?,
    val lateCount: Long,
    val isOuting: Boolean,
    val isBlackList: Boolean
)