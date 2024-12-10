package com.goms.v2.thirdparty.notification

import com.goms.v2.domain.notification.Notification
import com.goms.v2.domain.notification.spi.NotificationPort
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import org.springframework.stereotype.Component

@Component
class FcmAdapter: NotificationPort {

    private val firebaseInstance: FirebaseMessaging
        get() = FirebaseMessaging.getInstance()

    override fun sendNotification(deviceTokens: List<String>, notification: Notification) {
        val message = getMulticastMassageBuilderByNotification(notification)
            .addAllTokens(deviceTokens)
            .build()
        firebaseInstance.sendEachForMulticast(message)
    }

    private fun getMulticastMassageBuilderByNotification(notification: Notification) =
        with(notification) {
            MulticastMessage.builder()
                .setNotification(
                    com.google.firebase.messaging.Notification.builder()
                        .setTitle(title)
                        .setBody(content)
                        .build()
                )
        }

}