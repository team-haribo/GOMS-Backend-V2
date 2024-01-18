package com.goms.v2.domain.auth.dto.request

import javax.validation.constraints.NotBlank

data class SignInHttpRequest(
    @field:NotBlank
    val email: String,
    @field:NotBlank
    val password: String
)