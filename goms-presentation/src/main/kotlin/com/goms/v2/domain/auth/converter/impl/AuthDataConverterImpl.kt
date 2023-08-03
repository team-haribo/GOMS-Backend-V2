package com.goms.v2.domain.auth.converter.impl

import com.goms.v2.domain.auth.converter.AuthDataConverter
import com.goms.v2.domain.auth.dto.response.TokenDto
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AuthDataConverterImpl: AuthDataConverter {

    override fun toResponse(tokeInDto: TokenDto): TokenHttpResponse =
        TokenHttpResponse(
            accessToken = tokeInDto.accessToken,
            refreshToken = tokeInDto.refreshToken,
            accessTokenExp = LocalDateTime.now().plusSeconds(tokeInDto.accessTokenExp.toLong()),
            refreshTokenExp = LocalDateTime.now().plusSeconds(tokeInDto.refreshTokenExp.toLong()),
            authority = tokeInDto.authority
        )

}