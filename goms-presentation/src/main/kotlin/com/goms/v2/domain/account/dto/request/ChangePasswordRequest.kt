package com.goms.v2.domain.account.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class ChangePasswordRequest(
    @field:NotBlank
    val password: String,
    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#\\\$%^*+=-?<>])(?=.*[0-9]).{6,15}\$")
    val newPassword: String
)