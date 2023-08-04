package com.goms.v2.global.security.jwt

import com.goms.v2.domain.auth.spi.TokenParserPort
import org.springframework.stereotype.Component

@Component
class TokenParserAdapter(
    private val jwtParser: JwtParser
): TokenParserPort{

    override fun parseRefreshToken(refreshToken: String): String? =
        jwtParser.parseRefreshToken(refreshToken)

}