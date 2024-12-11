package com.goms.v2.thirdparty.notification

import com.goms.v2.domain.notification.NotificationConfig
import com.goms.v2.domain.notification.spi.NotificationPort
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import org.springframework.stereotype.Component

@Component
class FcmAdapter: NotificationPort {

    private val firebaseInstance: FirebaseMessaging
        get() = FirebaseMessaging.getInstance()

    override fun sendNotification(deviceTokens: List<String>, notificationConfig: NotificationConfig) {
        val notification = Notification.builder()
            .setTitle(notificationConfig.title)
            .setBody(notificationConfig.content)
            .build()

        val message: MulticastMessage = MulticastMessage.builder()
            .setNotification(notification)
            .addAllTokens(deviceTokens)
            .build()

        firebaseInstance.sendEachForMulticast(message)
    }

    private fun getMulticastMassageBuilderByNotification(notificationConfig: NotificationConfig) =
        with(notificationConfig) {
            MulticastMessage.builder()
                .setNotification(
                    com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(content)
                        .build()
                )
        }

}