package com.goms.v2.global.security.jwt

import com.goms.v2.domain.auth.spi.TokenParsePort
import org.springframework.stereotype.Component

@Component
class TokenParseAdapter(
    private val jwtParser: JwtParser
): TokenParsePort {

    override fun parseRefreshToken(refreshToken: String): String? =
        jwtParser.parseRefreshToken(refreshToken)

}