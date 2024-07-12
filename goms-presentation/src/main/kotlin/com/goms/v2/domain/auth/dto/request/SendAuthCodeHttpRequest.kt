package com.goms.v2.domain.auth.dto.request

import com.goms.v2.domain.auth.EmailStatus

data class SendAuthCodeHttpRequest(
    val email: String,
    val emailStatus: EmailStatus
)
