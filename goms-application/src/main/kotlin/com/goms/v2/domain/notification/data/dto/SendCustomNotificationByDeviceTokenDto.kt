package com.goms.v2.domain.notification.data.dto

data class SendCustomNotificationByDeviceTokenDto (
	val title: String,
	val content: String,
	val token: String,
	val deviceToken: String
)