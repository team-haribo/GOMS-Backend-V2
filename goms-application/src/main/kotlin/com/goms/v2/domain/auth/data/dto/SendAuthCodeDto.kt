package com.goms.v2.domain.auth.data.dto

import com.goms.v2.domain.auth.EmailStatus

data class SendAuthCodeDto(
    val email: String,
    val emailStatus: EmailStatus
)
