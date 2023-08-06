package com.goms.v2.persistence.email.mapper

import com.goms.v2.domain.email.Authentication
import com.goms.v2.persistence.email.entity.AuthenticationRedisEntity
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