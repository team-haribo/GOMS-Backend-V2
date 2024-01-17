package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import org.mapstruct.*
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AuthDataMapper {

    fun toResponse(tokenDto: TokenDto) =
        TokenHttpResponse(
            accessToken = tokenDto.accessToken,
            refreshToken = tokenDto.refreshToken,
            accessTokenExp = LocalDateTime.now().plusSeconds(tokenDto.accessTokenExp.toLong()),
            refreshTokenExp = LocalDateTime.now().plusSeconds(tokenDto.refreshTokenExp.toLong()),
            authority = tokenDto.authority
        )

}