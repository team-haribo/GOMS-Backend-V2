package com.goms.v2.domain.account.dto.request

data class ChangePasswordRequest(
    val password: String,
    val newPassword: String
)