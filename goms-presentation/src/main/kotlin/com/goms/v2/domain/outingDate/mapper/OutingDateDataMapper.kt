package com.goms.v2.domain.auth.mapper

import com.goms.v2.domain.auth.data.dto.SignInDto
import com.goms.v2.domain.auth.data.dto.SignUpDto
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.request.SignUpHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.outingDate.data.dto.SetTodayOutingDto
import com.goms.v2.domain.outingDate.dto.request.SetTodayOutingRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class OutingDateDataMapper {
	fun toDto(setTodayOutingRequest: SetTodayOutingRequest) =
		SetTodayOutingDto(
			outingStatus = setTodayOutingRequest.outingStatus,
			token = setTodayOutingRequest.token
		)
}