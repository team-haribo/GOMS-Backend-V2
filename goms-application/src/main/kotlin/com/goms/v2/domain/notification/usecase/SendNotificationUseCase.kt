package com.goms.v2.domain.notification.usecase

import com.goms.v2.common.annotation.UseCaseWithReadOnlyTransaction
import com.goms.v2.domain.notification.*
import com.goms.v2.domain.notification.spi.NotificationPort
import com.goms.v2.repository.notification.DeviceTokenRepository
import com.goms.v2.repository.outingDate.DeniedOutingDateRepository
import java.time.LocalDate

@UseCaseWithReadOnlyTransaction
class SendNotificationUseCase(
    private val notificationPort: NotificationPort,
    private val deviceTokenRepository: DeviceTokenRepository,
    private val deniedOutingDateRepository: DeniedOutingDateRepository,
) {

    fun execute(notificationType: NotificationType) {
        val isExistTodayOutingDate = deniedOutingDateRepository.existsByOutingDate(LocalDate.now())

        when (notificationType) {
            NotificationType.FIRST_NOTIFICATION -> {
                runCatching {
                    notificationPort.sendNotification(
                        deviceTokens = deviceTokenRepository.findAll().map { it.token },
                        notificationConfig = NotificationConfig(
                                title = if(isExistTodayOutingDate) Topic.DENIED_NOTIFICATION.title
                                    else Topic.FIRST_NOTIFICATION.title,
                                content = if(isExistTodayOutingDate) Topic.DENIED_NOTIFICATION.content
                                    else Topic.FIRST_NOTIFICATION.content,
                                writer = Writer.GOMS
                            )
                        )
                }.onFailure {
                    it.printStackTrace()
                }
            }

            NotificationType.FINAL_NOTIFICATION -> {
                if(!isExistTodayOutingDate){
                    runCatching {
                        notificationPort.sendNotification(
                            deviceTokens = deviceTokenRepository.findAll().map { it.token },
                            notificationConfig = NotificationConfig(
                                title = Topic.FINAL_NOTIFICATION.title,
                                content = Topic.FINAL_NOTIFICATION.content,
                                writer = Writer.GOMS
                            )
                        )
                    }.onFailure {
                        it.printStackTrace()
                    }
                }
            }
        }
    }
}
