package com.goms.v2.domain.notification.scheduler

import com.goms.v2.domain.notification.NotificationType
import com.goms.v2.domain.notification.usecase.SendNotificationUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NotificationScheduler(
    private val sendNotificationUseCase: SendNotificationUseCase
) {
    @Scheduled(cron = "0 30 17 ? * 1,3") // 매주 월요일, 수요일 5시 30분에 미리 공지한다. (1시간 10분 전)
    fun sendFirstNotification() =
        sendNotificationUseCase.execute(NotificationType.FIRST_NOTIFICATION)

    @Scheduled(cron = "0 35 18 ? * 1,3") // 매주 월요일, 수요일 6시 35분에 공지한다. (5분 전)
    fun sendSecondNotification() =
        sendNotificationUseCase.execute(NotificationType.FINAL_NOTIFICATION)
}