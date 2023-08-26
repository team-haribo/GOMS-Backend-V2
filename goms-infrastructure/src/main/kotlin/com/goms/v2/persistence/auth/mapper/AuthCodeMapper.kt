package com.goms.v2.persistence.auth.mapper

import com.goms.v2.domain.auth.AuthCode
import com.goms.v2.persistence.auth.entity.AuthCodeRedisEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface AuthCodeMapper {

    fun toEntity(authCode: AuthCode): AuthCodeRedisEntity
    fun toDomain(authCodeRedisEntity: AuthCodeRedisEntity?): AuthCode?

}