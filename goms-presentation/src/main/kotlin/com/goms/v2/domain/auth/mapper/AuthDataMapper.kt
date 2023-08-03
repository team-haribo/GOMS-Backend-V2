package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.dto.request.SignInDto
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenDto
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface AuthDataMapper {

    fun toDto(signInHttpRequest: SignInHttpRequest?): SignInDto

    @Mappings(
        Mapping(target = "accessTokenExp", expression = "java(LocalDateTime.now().plusSeconds(tokenDto.getAccessTokenExp()))"),
        Mapping(target = "refreshTokenExp", expression = "java(LocalDateTime.now().plusSeconds(tokenDto.getRefreshTokenExp()))")
    )
    fun toResponse(tokenDto: TokenDto): TokenHttpResponse

}