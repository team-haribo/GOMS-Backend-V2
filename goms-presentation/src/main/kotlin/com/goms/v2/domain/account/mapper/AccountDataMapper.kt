package com.goms.v2.domain.account.mapper

import com.goms.v2.domain.account.Account
import com.goms.v2.domain.account.data.dto.ProfileDto
import com.goms.v2.domain.account.dto.response.ProfileHttpResponse
import org.mapstruct.*
import org.springframework.stereotype.Component

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.WARN
)
@Component
interface AccountDataMapper {

    @Mappings(
        Mapping(target = "studentNum.grade", source = "studentNum.grade"),
        Mapping(target = "studentNum.classNum", source = "studentNum.classNum"),
        Mapping(target = "studentNum.number", source = "studentNum.number")
    )
    fun toEntity(profileDto: ProfileDto): ProfileHttpResponse

}