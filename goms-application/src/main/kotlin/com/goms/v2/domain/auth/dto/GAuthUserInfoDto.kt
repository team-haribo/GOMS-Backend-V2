package com.goms.v2.domain.auth.dto

data class GAuthUserInfoDto(
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val gender: String,
    val profileUrl: String,
    val role: String
)