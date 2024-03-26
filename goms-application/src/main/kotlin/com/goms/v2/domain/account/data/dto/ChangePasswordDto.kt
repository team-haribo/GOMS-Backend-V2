package com.goms.v2.domain.account.data.dto

data class ChangePasswordDto(
    val password: String,
    val newPassword: String
)