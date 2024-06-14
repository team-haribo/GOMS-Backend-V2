package com.goms.v2.domain.outing.mapper

import com.goms.v2.domain.outing.data.dto.OutingAccountDto
import com.goms.v2.domain.outing.dto.response.OutingAccountHttpResponse
import com.goms.v2.domain.outing.dto.response.OutingCountHttpResponse
import org.springframework.stereotype.Component

@Component
class OutingDataMapper {

    fun toResponse(outingAccountDto: OutingAccountDto) =
        OutingAccountHttpResponse(
            accountIdx = outingAccountDto.accountIdx,
            name = outingAccountDto.name,
            grade = outingAccountDto.grade,
            major = outingAccountDto.major,
            gender = outingAccountDto.gender,
            profileUrl = outingAccountDto.profileUrl,
            createdTime = outingAccountDto.createdTime
        )

    fun toResponse(outingCount: Long) =
        OutingCountHttpResponse(
            outingCount = outingCount
        )

}