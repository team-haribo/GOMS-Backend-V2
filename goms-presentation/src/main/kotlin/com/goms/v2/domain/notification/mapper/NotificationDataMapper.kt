package com.goms.v2.domain.notification.mapper

import com.goms.v2.domain.notification.data.dto.SendCustomNotificationByDeviceTokenDto
import com.goms.v2.domain.notification.data.dto.SendCustomNotificationDto
import com.goms.v2.domain.notification.dto.request.SendCustomNotificationByDeviceTokenRequest
import com.goms.v2.domain.notification.dto.request.SendCustomNotificationRequest
import org.springframework.stereotype.Component

@Component
class NotificationDataMapper {
    fun toDto(sendCustomNotificationRequest: SendCustomNotificationRequest, discordClientToken: String) =
        SendCustomNotificationDto(
            title = sendCustomNotificationRequest.title,
            content = sendCustomNotificationRequest.content,
            token = discordClientToken
        )

    fun toDto(
        sendCustomNotificationByDeviceTokenRequest: SendCustomNotificationByDeviceTokenRequest,
        discordClientToken: String,
        deviceToken: String
    ) = SendCustomNotificationByDeviceTokenDto(
        title = sendCustomNotificationByDeviceTokenRequest.title,
        content = sendCustomNotificationByDeviceTokenRequest.content,
        token = discordClientToken,
        deviceToken = deviceToken
    )
}
