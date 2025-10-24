package com.goms.v2.thirdparty.notification.config

import com.goms.v2.thirdparty.notification.property.FcmProperties
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct

private val log = KotlinLogging.logger { }

@Configuration
class FcmConfig(
    private val fcmProperties: FcmProperties
) {
    @PostConstruct
    fun init() {
        log.info("init start")
        runCatching {
            val options = FirebaseOptions.builder()
                .setCredentials(
                    GoogleCredentials.fromStream(
                        fcmProperties.credential.byteInputStream(Charsets.UTF_8)
                    )
                )
                .build()

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options)
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

}