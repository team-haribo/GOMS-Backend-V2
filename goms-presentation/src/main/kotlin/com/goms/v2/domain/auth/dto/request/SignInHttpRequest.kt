package com.goms.v2.domain.auth.dto.request

import org.jetbrains.annotations.NotNull

data class SignInHttpRequest(
    @field:NotNull
    val code: String
)