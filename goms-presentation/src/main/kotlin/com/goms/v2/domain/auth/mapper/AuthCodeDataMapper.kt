package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.data.dto.SendAuthCodeDto
import com.goms.v2.domain.auth.dto.request.SendAuthCodeHttpRequest
import org.springframework.stereotype.Component

@Component
class AuthCodeDataMapper {

    fun toDto(request: SendAuthCodeHttpRequest): SendAuthCodeDto =
        SendAuthCodeDto(
            email = request.email
        )

}