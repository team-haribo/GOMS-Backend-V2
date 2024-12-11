package com.goms.v2.domain.notification.mapper

import com.fasterxml.jackson.annotation.ObjectIdGenerators.StringIdGenerator
import com.goms.v2.domain.auth.data.dto.SignInDto
import com.goms.v2.domain.auth.data.dto.SignUpDto
import com.goms.v2.domain.auth.data.dto.TokenDto
import com.goms.v2.domain.auth.dto.request.SignInHttpRequest
import com.goms.v2.domain.auth.dto.request.SignUpHttpRequest
import com.goms.v2.domain.auth.dto.response.TokenHttpResponse
import com.goms.v2.domain.notification.data.dto.SendCustomNotificationDto
import com.goms.v2.domain.notification.dto.request.SendCustomNotificationRequest
import com.goms.v2.domain.outingDate.data.dto.SetTodayOutingDto
import com.goms.v2.domain.outingDate.dto.request.SetTodayOutingRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class NotificationDataMapper {
	fun toDto(sendCustomNotificationRequest: SendCustomNotificationRequest, discordClientToken: String) =
		SendCustomNotificationDto(
			title = sendCustomNotificationRequest.title,
			content = sendCustomNotificationRequest.content,
			token = discordClientToken
		)
}
