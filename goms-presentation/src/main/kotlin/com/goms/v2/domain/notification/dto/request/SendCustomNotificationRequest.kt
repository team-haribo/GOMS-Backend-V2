package com.goms.v2.domain.notification.dto.request

import javax.validation.constraints.NotEmpty

data class SendCustomNotificationRequest (
	@field:NotEmpty
	val title: String,
	@field:NotEmpty
	val content: String
)