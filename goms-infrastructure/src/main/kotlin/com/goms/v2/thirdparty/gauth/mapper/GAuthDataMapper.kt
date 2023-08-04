package com.goms.v2.thirdparty.gauth.mapper

import com.goms.v2.domain.auth.data.dto.GAuthTokenDto
import com.goms.v2.domain.auth.data.dto.GAuthUserInfoDto
import gauth.GAuthToken
import gauth.GAuthUserInfo
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
)
interface GAuthDataMapper {

    fun toDto(gAuthUserInfo: GAuthUserInfo): GAuthUserInfoDto
    fun toDto(gAuthToken: GAuthToken): GAuthTokenDto

}