package com.goms.v2.global.security.adapter

import com.goms.v2.domain.auth.spi.PasswordEncoderPort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncodeAdapter(
    private val passwordEncoder: PasswordEncoder
) : PasswordEncoderPort {

    override fun passwordEncode(password: String): String =
        passwordEncoder.encode(password)

    override fun isPasswordMatch(rawPassword: String, encodedPassword: String): Boolean =
        passwordEncoder.matches(rawPassword, encodedPassword)

}