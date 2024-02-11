package com.goms.v2.domain.studentCouncil.data.dto

import com.goms.v2.domain.account.constant.Authority
import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major

data class SearchAccountDto(
    val grade: Int?,
    val gender: Gender?,
    val name: String?,
    val authority: Authority?,
    val isBlackList: Boolean?,
    val major: Major?
)