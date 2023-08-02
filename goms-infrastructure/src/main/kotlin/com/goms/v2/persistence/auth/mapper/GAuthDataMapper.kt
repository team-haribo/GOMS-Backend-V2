package com.goms.v2.persistence.auth.mapper

import com.goms.v2.domain.auth.dto.GAuthExceptionDto
import com.goms.v2.domain.auth.dto.GAuthTokenDto
import com.goms.v2.domain.auth.dto.GAuthUserInfoDto
import gauth.GAuthToken
import gauth.GAuthUserInfo
import gauth.exception.GAuthException
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
interface GAuthDataMapper {

    fun toDto(gAuthUserInfo: GAuthUserInfo): GAuthUserInfoDto
    fun toDto(gAuthToken: GAuthToken): GAuthTokenDto
    fun toDto(gAuthException: GAuthException): GAuthExceptionDto

}