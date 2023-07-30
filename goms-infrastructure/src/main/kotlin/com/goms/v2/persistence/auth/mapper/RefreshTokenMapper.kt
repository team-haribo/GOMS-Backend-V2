package com.goms.v2.persistence.auth.mapper

import com.goms.v2.domain.auth.RefreshToken
import com.goms.v2.persistence.auth.entity.RefreshTokenJpaEntity
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN,
)
interface RefreshTokenMapper {
    @Mappings()
    fun toEntity(refreshToken: RefreshToken): RefreshTokenJpaEntity

}