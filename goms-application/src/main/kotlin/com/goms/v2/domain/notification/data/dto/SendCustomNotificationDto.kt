package com.goms.v2.domain.notification.data.dto

data class SendCustomNotificationDto (
	val title: String,
	val content: String,
	val token: String
)