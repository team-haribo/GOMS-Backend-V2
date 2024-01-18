package com.goms.v2.domain.auth.dto.request

import com.goms.v2.domain.account.constant.Gender
import org.jetbrains.annotations.NotNull

data class SignUpHttpRequest(
    @field:NotNull
    val phoneNumber: String,
    @field:NotNull
    val password: String,
    @field:NotNull
    val name: String,
    @field:NotNull
    val grade: Int,
    @field:NotNull
    val gender: Gender
)