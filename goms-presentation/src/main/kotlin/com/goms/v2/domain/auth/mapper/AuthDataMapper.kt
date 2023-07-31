package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.dto.request.SignInDto
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.dto.response.TokenDto
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
interface AuthDataMapper {

    fun toResponse(tokeInDto: TokenDto?): TokenHttpResponse
    fun toDto(signInHttpRequest: SignInHttpRequest?): SignInDto

}