package com.goms.v2.persistence.outing.mapper

import com.goms.v2.domain.outing.Outing
import com.goms.v2.persistence.outing.entity.OutingJpaEntity
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.Mappings
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface OutingMapper {

    @Mappings(
        Mapping(target = "account.idx", source = "account.idx"),
        Mapping(target = "account.email", source = "account.email"),
        Mapping(target = "account.studentNumber", source = "account.studentNumber"),
        Mapping(target = "account.name", source = "account.name"),
        Mapping(target = "account.profileUrl", source = "account.profileUrl"),
        Mapping(target = "account.authority", source = "account.authority"),
        Mapping(target = "account.createdTime", source = "account.createdTime")
    )
    fun toEntity(outing: Outing): OutingJpaEntity

    @Mappings(
        Mapping(target = "account.idx", source = "account.idx"),
        Mapping(target = "account.email", source = "account.email"),
        Mapping(target = "account.studentNumber", source = "account.studentNumber"),
        Mapping(target = "account.name", source = "account.name"),
        Mapping(target = "account.profileUrl", source = "account.profileUrl"),
        Mapping(target = "account.authority", source = "account.authority"),
        Mapping(target = "account.createdTime", source = "account.createdTime")
    )
    fun toDomain(outingJpaEntity: OutingJpaEntity): Outing

}