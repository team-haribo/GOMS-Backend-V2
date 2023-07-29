package com.goms.v2.domain.auth.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import org.jetbrains.annotations.NotNull

data class SignInWebRequest @JsonCreator constructor(
    @field:NotNull
    val code: String
) {
    fun toData() = SignInRequest(
        code = code
    )
}