package com.goms.v2.domain.email.mapper

import com.goms.v2.domain.email.data.dto.EmailRequestDto
import com.goms.v2.domain.email.dto.request.SendEmailHttpRequest
import org.mapstruct.InjectionStrategy
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy
import org.springframework.stereotype.Component

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@Component
interface EmailAuthDataMapper {

    fun toDto(sendEmailHttpRequest: SendEmailHttpRequest): EmailRequestDto

}