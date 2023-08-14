package com.goms.v2.persistence.outing.mapper

import com.goms.v2.domain.outing.OutingBlackList
import com.goms.v2.persistence.outing.entity.OutingBlackListRedisEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface OutingBlackListMapper {

    fun toEntity(outingBlacklist: OutingBlackList): OutingBlackListRedisEntity
    fun toDomain(outingBlackListRedisEntity: OutingBlackListRedisEntity): OutingBlackList

}