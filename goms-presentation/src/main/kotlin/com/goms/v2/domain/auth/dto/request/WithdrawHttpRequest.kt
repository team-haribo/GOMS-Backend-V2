package com.goms.v2.domain.auth.dto.request

import javax.validation.constraints.NotBlank

data class WithdrawHttpRequest(
    @field:NotBlank
    val password: String
)