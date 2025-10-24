package com.goms.v2.domain.notification.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.notification.NotificationConfig
import com.goms.v2.domain.notification.Writer
import com.goms.v2.domain.notification.data.dto.SendCustomNotificationDto
import com.goms.v2.domain.notification.spi.NotificationPort
import com.goms.v2.domain.outingDate.exception.UnauthorizedDiscordClientTokenException
import com.goms.v2.repository.notification.DeviceTokenRepository
import org.springframework.beans.factory.annotation.Value

@UseCaseWithReadOnlyTransaction
class SendCustomNotificationUseCase(
    @param:Value("\${discord.api.token}")
    private val discordToken: String,
    private val notificationPort: NotificationPort,
    private val deviceTokenRepository: DeviceTokenRepository,
) {

    fun execute(sendCustomNotificationDto: SendCustomNotificationDto) {
        if (sendCustomNotificationDto.token != discordToken) {
            throw UnauthorizedDiscordClientTokenException()
        }

        runCatching {
            notificationPort.sendNotification(
                deviceTokens = deviceTokenRepository.findAll()
                    .flatMap { it.token }
                    .distinct(),
                notificationConfig = NotificationConfig(
                    title = sendCustomNotificationDto.title,
                    content = sendCustomNotificationDto.content,
                    writer = Writer.GOMS
                )
            )
        }.onFailure {
            it.printStackTrace()
        }
    }
}
