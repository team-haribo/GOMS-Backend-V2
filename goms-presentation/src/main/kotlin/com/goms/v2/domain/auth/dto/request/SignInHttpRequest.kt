package com.goms.v2.domain.auth.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import org.jetbrains.annotations.NotNull

data class SignInHttpRequest @JsonCreator constructor(
    @field:NotNull
    val code: String
) {
    fun toData() = SignInDto(
        code = code
    )
}