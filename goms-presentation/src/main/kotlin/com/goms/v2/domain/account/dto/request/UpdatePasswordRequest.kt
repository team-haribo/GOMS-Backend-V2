package com.goms.v2.domain.account.dto.request

import javax.validation.constraints.NotBlank

data class UpdatePasswordRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val newPassword: String
)