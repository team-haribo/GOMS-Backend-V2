package com.goms.v2.persistence.auth.mapper

import com.goms.v2.domain.auth.RefreshToken
import com.goms.v2.persistence.auth.entity.RefreshTokenJpaEntity
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface RefreshTokenMapper {

    fun toEntity(refreshToken: RefreshToken): RefreshTokenJpaEntity
    fun toDomain(refreshTokenJpaEntity: RefreshTokenJpaEntity): RefreshToken

}