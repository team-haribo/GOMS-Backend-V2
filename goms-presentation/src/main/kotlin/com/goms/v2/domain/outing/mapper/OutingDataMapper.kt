package com.goms.v2.domain.outing.mapper

import com.goms.v2.domain.outing.data.dto.OutingAccountDto
import com.goms.v2.domain.outing.dto.response.OutingAccountHttpResponse
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN
)
interface OutingDataMapper {

    @Mappings(
        Mapping(target = "studentNum.grade", source = "studentNum.grade"),
        Mapping(target = "studentNum.classNum", source = "studentNum.classNum"),
        Mapping(target = "studentNum.number", source = "studentNum.number")
    )
    fun toResponse(outingAccountDto: OutingAccountDto): OutingAccountHttpResponse

}