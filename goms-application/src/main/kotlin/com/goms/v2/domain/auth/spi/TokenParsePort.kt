package com.goms.v2.domain.auth.spi


interface TokenParsePort {

    fun parseRefreshToken(refreshToken: String): String?

}