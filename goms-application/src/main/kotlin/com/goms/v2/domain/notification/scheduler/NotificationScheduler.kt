package com.goms.v2.domain.notification.scheduler

import com.goms.v2.domain.notification.NotificationType
import com.goms.v2.domain.notification.usecase.SendNotificationUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NotificationScheduler(
    private val sendNotificationUseCase: SendNotificationUseCase
) {

    @Scheduled(cron = "0 20 18 ? * 3") // 매주 수요일 6시 20분에 외출 여부 디스코드를 보낸다.
    fun sendNotificationAtBeforeOuting() = sendNotificationUseCase.execute(NotificationType.BEFORE_OUTING)

    @Scheduled(cron = "0 20 19 ? * 3") // 매주 수요일 7시 20분에 외출 여부 디스코드를 보낸다.
    fun sendNotificationAtAfterOuting() = sendNotificationUseCase.execute(NotificationType.AFTER_OUTING)

}