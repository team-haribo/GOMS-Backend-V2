package com.goms.v2.domain.studentCouncil.data.dto

import com.goms.v2.domain.account.Authority

data class SearchAccountDto(
    val grade: Int?,
    val classNum: Int?,
    val name: String?,
    val authority: Authority?,
    val isBlackList: Boolean?
)
