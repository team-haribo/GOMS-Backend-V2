package com.goms.v2.domain.auth.dto.request

import com.goms.v2.domain.auth.EmailStatus
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class SendAuthCodeHttpRequest(
    @field:NotBlank
    val email: String,
    @field:NotNull
    val emailStatus: EmailStatus
)
