package com.goms.v2.persistence.email.mapper

import com.goms.v2.domain.email.EmailAuth
import com.goms.v2.persistence.email.entity.EmailAuthRedisEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@Component
interface EmailAuthMapper {


    fun toEntity(emailAuth: EmailAuth): EmailAuthRedisEntity

    fun toDomain(emailAuthRedisEntity: EmailAuthRedisEntity?): EmailAuth?

}