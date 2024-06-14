package com.goms.v2.domain.studentCouncil.data.dto

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import java.util.UUID

data class AccountDto(
    val accountIdx: UUID,
    val name: String,
    val grade: Int,
    val gender: Gender,
    val major: Major,
    val profileUrl: String?,
    val authority: Authority,
    val isBlackList: Boolean
)
