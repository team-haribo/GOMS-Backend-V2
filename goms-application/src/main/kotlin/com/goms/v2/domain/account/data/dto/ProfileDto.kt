package com.goms.v2.domain.account.data.dto

import com.goms.v2.domain.account.Authority
import java.util.*

data class ProfileDto(
    val name: String,
    val studentNum: StudentNumberDto,
    val authority: Authority,
    val profileUrl: String?,
    val lateCount: Long,
    val isOuting: Boolean,
    val isBlackList: Boolean
)