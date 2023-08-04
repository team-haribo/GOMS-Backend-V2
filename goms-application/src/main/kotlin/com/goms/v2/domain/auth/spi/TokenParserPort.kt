package com.goms.v2.domain.auth.spi


interface TokenParserPort {

    fun parseRefreshToken(refreshToken: String): String?

}