package com.goms.v2.domain.account.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class UpdatePasswordRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    @field:Pattern(regexp = "^(?=(?>[^a-zA-Z]*[a-zA-Z]))(?=(?>[^!@#$%^*+=\\-?<>]*[!@#$%^*+=\\-?<>]))(?=(?>[^0-9]*[0-9])).{6,15}$")
    val newPassword: String
)