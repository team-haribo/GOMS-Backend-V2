package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.dto.request.SignInDto
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenDto
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import org.mapstruct.*
import java.time.LocalDateTime

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface AuthDataMapper {

    fun toDto(signInHttpRequest: SignInHttpRequest?): SignInDto

    fun toResponse(tokeInDto: TokenDto): TokenHttpResponse =
        TokenHttpResponse(
            accessToken = tokeInDto.accessToken,
            refreshToken = tokeInDto.refreshToken,
            accessTokenExp = LocalDateTime.now().plusSeconds(tokeInDto.accessTokenExp.toLong()),
            refreshTokenExp = LocalDateTime.now().plusSeconds(tokeInDto.refreshTokenExp.toLong()),
            authority = tokeInDto.authority
        )

}