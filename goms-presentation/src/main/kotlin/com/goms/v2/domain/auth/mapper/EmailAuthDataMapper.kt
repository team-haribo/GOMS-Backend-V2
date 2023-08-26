package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.data.dto.EmailDto
import com.goms.v2.domain.auth.dto.request.SendEmailHttpRequest
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface EmailAuthDataMapper {

    fun toDto(sendEmailHttpRequest: SendEmailHttpRequest): EmailDto

}