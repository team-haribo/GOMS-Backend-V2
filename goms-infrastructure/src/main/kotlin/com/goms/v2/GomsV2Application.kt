package com.goms.v2

import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDateTime
import java.util.*
import javax.annotation.PostConstruct

private val log = KotlinLogging.logger {}

@SpringBootApplication
class GomsV2Application {

    companion object {
        init {
            System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true")
        }
    }

    @PostConstruct
    fun applicationTimeZoneSetter() {
        val timeZone = TimeZone.getTimeZone("Asia/Seoul")
        TimeZone.setDefault(timeZone)

        log.info { "Goms Application TimeZone was set: ${LocalDateTime.now()}" }
    }

}

fun main(args: Array<String>) {
    runApplication<GomsV2Application>(*args)
}
