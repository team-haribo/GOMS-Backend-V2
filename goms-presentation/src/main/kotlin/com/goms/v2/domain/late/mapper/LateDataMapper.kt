package com.goms.v2.domain.late.mapper

import com.goms.v2.domain.late.data.dto.LateRankDto
import com.goms.v2.domain.late.dto.LateRankHttpResponse
import org.mapstruct.*
import org.springframework.stereotype.Component

@Component
class LateDataMapper {

    fun toResponse(lateRankDto: LateRankDto) =
        LateRankHttpResponse(
            accountIdx = lateRankDto.accountIdx,
            name = lateRankDto.name,
            grade = lateRankDto.grade,
            gender = lateRankDto.gender,
            profileUrl = lateRankDto.profileUrl
        )

}