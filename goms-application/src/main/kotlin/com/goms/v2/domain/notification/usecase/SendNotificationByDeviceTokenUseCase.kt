package com.goms.v2.domain.notification.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.notification.*
import com.goms.v2.domain.notification.data.dto.SendCustomNotificationByDeviceTokenDto
import com.goms.v2.domain.notification.spi.NotificationPort
import com.goms.v2.domain.outingDate.exception.UnauthorizedDiscordClientTokenException
import org.springframework.beans.factory.annotation.Value

@UseCaseWithReadOnlyTransaction
class SendNotificationByDeviceTokenUseCase(
    @Value("\${discord.api.token}")
    private val discordToken: String,
    private val notificationPort: NotificationPort,
) {

    fun execute(sendCustomNotificationByDeviceTokenDto: SendCustomNotificationByDeviceTokenDto) {
        if(sendCustomNotificationByDeviceTokenDto.token != discordToken){
            throw UnauthorizedDiscordClientTokenException()
        }

        runCatching {
            notificationPort.sendNotification(
                deviceTokens = listOf(sendCustomNotificationByDeviceTokenDto.deviceToken),
                notificationConfig = NotificationConfig(
                    title = sendCustomNotificationByDeviceTokenDto.title,
                    content = sendCustomNotificationByDeviceTokenDto.content,
                    writer = Writer.GOMS
                )
            )
        }.onFailure {
            it.printStackTrace()
        }
    }
}
