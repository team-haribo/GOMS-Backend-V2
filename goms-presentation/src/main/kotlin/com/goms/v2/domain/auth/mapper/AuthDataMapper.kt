package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.data.dto.SignInDto
import com.goms.v2.domain.auth.data.dto.SignUpDto
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.request.SignUpHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
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

    fun toDto(signUpHttpRequest: SignUpHttpRequest) =
        SignUpDto(
            email = signUpHttpRequest.email,
            password = signUpHttpRequest.password,
            name = signUpHttpRequest.name,
            major = signUpHttpRequest.major,
            gender = signUpHttpRequest.gender
        )

    fun toDto(signInHttpRequest: SignInHttpRequest) =
        SignInDto(
            email = signInHttpRequest.email,
            password = signInHttpRequest.password
        )

}