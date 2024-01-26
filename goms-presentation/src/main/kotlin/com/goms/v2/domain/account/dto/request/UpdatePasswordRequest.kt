package com.goms.v2.domain.account.dto.request

data class UpdatePasswordRequest(
    val email: String,
    val newPassword: String
)