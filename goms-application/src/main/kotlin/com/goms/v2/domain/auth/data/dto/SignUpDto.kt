package com.goms.v2.domain.auth.data.dto

import com.goms.v2.domain.account.constant.Major
import com.goms.v2.domain.account.constant.Gender

data class SignUpDto(
    val email: String,
    val password: String,
    val name: String,
    val gender: Gender,
    val major: Major
)