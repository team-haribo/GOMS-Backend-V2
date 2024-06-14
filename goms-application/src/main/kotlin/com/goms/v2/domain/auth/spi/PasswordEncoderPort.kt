package com.goms.v2.domain.auth.spi

interface PasswordEncoderPort {

    fun passwordEncode(password: String): String
    fun isPasswordMatch(rawPassword: String, encodedPassword: String): Boolean

}