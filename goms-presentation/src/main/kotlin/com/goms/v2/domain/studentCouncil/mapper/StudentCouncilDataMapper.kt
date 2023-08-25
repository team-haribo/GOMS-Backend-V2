package com.goms.v2.domain.studentCouncil.mapper

import com.goms.v2.domain.account.Authority
import com.goms.v2.domain.studentCouncil.data.dto.AccountDto
import com.goms.v2.domain.studentCouncil.data.dto.GrantAuthorityDto
import com.goms.v2.domain.studentCouncil.data.dto.SearchAccountDto
import com.goms.v2.domain.studentCouncil.dto.request.GrantAuthorityHttpRequest
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

    fun toDto(request: GrantAuthorityHttpRequest): GrantAuthorityDto

    @Mappings(
        Mapping(target = "studentNum.grade", source = "studentNum.grade"),
        Mapping(target = "studentNum.classNum", source = "studentNum.classNum"),
        Mapping(target = "studentNum.number", source = "studentNum.number")
    )
    fun toResponse(dto: List<AccountDto>): List<AllAccountHttpResponse>

    fun toDto(
        grade: Int?,
        classNum: Int?,
        name: String?,
        authority: Authority?,
        isBlackList: Boolean?
    ): SearchAccountDto

}