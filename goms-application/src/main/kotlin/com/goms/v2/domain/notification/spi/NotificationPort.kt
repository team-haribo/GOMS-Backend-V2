package com.goms.v2.domain.notification.spi

import com.goms.v2.domain.notification.NotificationConfig

interface NotificationPort {

    fun sendNotification(deviceTokens: List<String>, notificationConfig: NotificationConfig)

}
