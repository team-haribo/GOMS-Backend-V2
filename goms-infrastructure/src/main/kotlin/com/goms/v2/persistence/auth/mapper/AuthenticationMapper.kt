package com.goms.v2.persistence.auth.mapper

import com.goms.v2.domain.auth.Authentication
import com.goms.v2.persistence.auth.entity.AuthenticationRedisEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface AuthenticationMapper {

    fun toEntity(authentication: Authentication): AuthenticationRedisEntity
    fun toDomain(authenticationRedisEntity: AuthenticationRedisEntity?): Authentication

}