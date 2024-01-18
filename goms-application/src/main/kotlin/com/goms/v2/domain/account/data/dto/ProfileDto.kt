package com.goms.v2.domain.account.data.dto

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender

data class ProfileDto(
    val name: String,
    val grade: Int,
    val gender: Gender,
    val authority: Authority,
    val profileUrl: String?,
    val lateCount: Long,
    val isOuting: Boolean,
    val isBlackList: Boolean
)