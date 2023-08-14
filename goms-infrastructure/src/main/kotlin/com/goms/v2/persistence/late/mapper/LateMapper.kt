package com.goms.v2.persistence.late.mapper

import com.goms.v2.domain.late.Late
import com.goms.v2.persistence.late.entity.LateJpaEntity
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface LateMapper {

    @Mappings(
        Mapping(target = "account.idx", source = "account.idx"),
        Mapping(target = "account.email", source = "account.email"),
        Mapping(target = "account.studentNumber", source = "account.studentNumber"),
        Mapping(target = "account.name", source = "account.name"),
        Mapping(target = "account.profileUrl", source = "account.profileUrl"),
        Mapping(target = "account.authority", source = "account.authority"),
        Mapping(target = "account.createdTime", source = "account.createdTime")
    )
    fun toEntity(late: Late): LateJpaEntity

}