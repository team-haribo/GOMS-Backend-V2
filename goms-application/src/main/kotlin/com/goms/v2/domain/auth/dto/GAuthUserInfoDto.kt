package com.goms.v2.domain.auth.dto

data class GAuthUserInfoDto(
    val email: String,
    val name: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val profileUrl: String,
)