package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.dto.request.SignInDto
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface AuthDataMapper {

    fun toDto(signInHttpRequest: SignInHttpRequest?): SignInDto

}