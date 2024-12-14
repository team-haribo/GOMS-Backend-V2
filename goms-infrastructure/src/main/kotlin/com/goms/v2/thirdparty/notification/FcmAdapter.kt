package com.goms.v2.thirdparty.notification

import com.goms.v2.domain.notification.NotificationConfig
import com.goms.v2.domain.notification.spi.NotificationPort
import com.google.firebase.messaging.*
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

        // iOS Sound
        val aps = Aps.builder()
            .setSound(
                CriticalSound.builder()
                    .setVolume(1.0)
                    .setName("default")
                    .setCritical(true)
                    .build()
            )
            .build()

        val apnsConfig = ApnsConfig.builder()
            .setAps(aps)
            .build()

        // Android Sound
        val androidNotification = AndroidNotification.builder()
            .setSound("default")
            .build()

        val androidConfig = AndroidConfig.builder()
            .setNotification(androidNotification)
            .build()

        val message: MulticastMessage = MulticastMessage.builder()
            .setNotification(notification)
            .addAllTokens(deviceTokens)
            .setApnsConfig(apnsConfig)
            .setAndroidConfig(androidConfig)
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