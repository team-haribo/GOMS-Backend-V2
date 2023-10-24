package com.goms.v2.domain

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping
    fun healthCheck(): ResponseEntity<Map<String, String>> =
        ResponseEntity.ok(mapOf("message" to "Goms-V2 server running"))

}