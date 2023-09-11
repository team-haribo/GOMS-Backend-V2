package com.goms.v2.domain.account.mapper

import com.goms.v2.domain.account.data.dto.ProfileDto
import com.goms.v2.domain.account.dto.response.ProfileHttpResponse
import org.mapstruct.*

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN
)
interface AccountDataMapper {

    @Mappings(
        Mapping(target = "studentNum.grade", source = "studentNum.grade"),
        Mapping(target = "studentNum.classNum", source = "studentNum.classNum"),
        Mapping(target = "studentNum.number", source = "studentNum.number"),
        Mapping(target = "isOuting", source = "outing"),
        Mapping(target = "isBlackList", source = "blackList")
    )
    fun toResponse(profileDto: ProfileDto): ProfileHttpResponse

}