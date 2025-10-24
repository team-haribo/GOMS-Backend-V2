package com.goms.v2.domain.outingDate.mapper

import com.goms.v2.domain.outingDate.data.dto.SetTodayOutingDto
import com.goms.v2.domain.outingDate.dto.request.SetTodayOutingRequest
import org.springframework.stereotype.Component

@Component
class OutingDateDataMapper {
    fun toDto(setTodayOutingRequest: SetTodayOutingRequest, discordClientToken: String) =
        SetTodayOutingDto(
            outingStatus = setTodayOutingRequest.outingStatus,
            token = discordClientToken
        )
}
