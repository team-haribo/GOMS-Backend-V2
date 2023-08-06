package com.goms.v2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
class GomsV2Application

fun main(args: Array<String>) {
    runApplication<GomsV2Application>(*args)
}
