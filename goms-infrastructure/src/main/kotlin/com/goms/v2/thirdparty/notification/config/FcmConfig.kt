package com.goms.v2.thirdparty.notification.config

import com.goms.v2.thirdparty.notification.property.FcmProperties
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import mu.KotlinLogging
import org.springframework.context.annotation.Configuration
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import javax.annotation.PostConstruct

private val log = KotlinLogging.logger {  }

@Configuration
class FcmConfig(
    private val fcmProperties: FcmProperties
) {

    companion object {
        const val PATH = "./credentials.json"
    }

    @PostConstruct
    fun init() {
        log.info("init start")
        runCatching {
            URL(fcmProperties.fileUrl).openStream().use {
                Files.copy(it, Paths.get(PATH))
                val file = File(PATH)
                if (FirebaseApp.getApps().isEmpty()) {
                    val options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(file.inputStream()))
                        .build()
                    FirebaseApp.initializeApp(options)
                }
                file.delete()
            }
        }.onFailure {
            it.printStackTrace()
        }
    }

}