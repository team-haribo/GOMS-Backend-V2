package com.goms.v2.domain.auth.dto.request

import com.goms.v2.domain.account.constant.Gender
import com.goms.v2.domain.account.constant.Major
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class SignUpHttpRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    @field:Pattern(regexp = "^(?=.[a-zA-Z])(?=.[!@#\$%^&?~])[a-zA-Z!@#\$%^&?~]{6,15}\$")
    val password: String,
    @field:NotBlank
    val name: String,
    @field:NotNull
    val major: Major,
    @field:NotNull
    val gender: Gender
)