package com.goms.v2.domain.notification.spi

import com.goms.v2.domain.notification.Notification

interface NotificationPort {

    fun sendNotification(deviceTokens: List<String>, notification: Notification)

}
