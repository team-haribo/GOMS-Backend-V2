package com.goms.v2.persistence.account.mapper

import com.goms.v2.domain.account.Account
import com.goms.v2.persistence.account.entity.AccountJpaEntity
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN,
)
interface AccountMapper {

    @Mappings(
        Mapping(target = "studentNumber.grade", source = "studentNumber.grade"),
        Mapping(target = "studentNumber.classNum", source = "studentNumber.classNum"),
        Mapping(target = "studentNumber.number", source = "studentNumber.number")
    )
    fun toDomain(accountJpaEntity: AccountJpaEntity?): Account

    @Mappings(
        Mapping(target = "studentNumber.grade", source = "studentNumber.grade"),
        Mapping(target = "studentNumber.classNum", source = "studentNumber.classNum"),
        Mapping(target = "studentNumber.number", source = "studentNumber.number")
    )
    fun toEntity(account: Account): AccountJpaEntity

}