package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.dto.request.SignInDto
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.auth.dto.response.TokenInDto
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN,
)
interface AuthDataMapper {

    fun toResponse(tokeInDto: TokenInDto?): TokenHttpResponse

    fun toDto(signInHttpRequest: SignInHttpRequest?): SignInDto

}