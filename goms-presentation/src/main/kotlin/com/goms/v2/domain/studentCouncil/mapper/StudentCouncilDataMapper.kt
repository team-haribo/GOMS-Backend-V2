package com.goms.v2.domain.studentCouncil.mapper

import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.domain.studentCouncil.dto.response.AllAccountHttpResponse
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN
)
interface StudentCouncilDataMapper {

    @Mappings(
        Mapping(target = "studentNum.grade", source = "studentNum.grade"),
        Mapping(target = "studentNum.classNum", source = "studentNum.classNum"),
        Mapping(target = "studentNum.number", source = "studentNum.number")
    )
    fun toResponse(accountDto: AccountDto): AllAccountHttpResponse

}