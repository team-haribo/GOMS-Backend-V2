package com.goms.v2.domain.notification.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.notification.*
import com.goms.v2.domain.notification.data.dto.SendCustomNotificationDto
import com.goms.v2.domain.notification.spi.NotificationPort
import com.goms.v2.domain.outingDate.exception.UnauthorizedDiscordClientTokenException
import com.goms.v2.repository.notification.DeviceTokenRepository
import com.goms.v2.repository.outingDate.DeniedOutingDateRepository
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDate

@UseCaseWithReadOnlyTransaction
class SendCustomNotificationUseCase(
    @Value("\${discord.api.token}")
    private val discordToken: String,
    private val notificationPort: NotificationPort,
    private val deviceTokenRepository: DeviceTokenRepository,
) {

    fun execute(sendCustomNotificationDto: SendCustomNotificationDto) {
        if(sendCustomNotificationDto.token != discordToken){
            throw UnauthorizedDiscordClientTokenException()
        }

        runCatching {
            notificationPort.sendNotification(
                deviceTokens = deviceTokenRepository.findAll().map { it.token },
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
