package com.goms.v2.domain.notification

import com.goms.v2.domain.notification.usecase.DeleteDeviceTokenUseCase
import com.goms.v2.domain.notification.usecase.SendNotificationUseCase
import com.goms.v2.domain.notification.usecase.SetDeviceTokenUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v2/notification")
class NotificationController(
    private val setDeviceTokenUseCase: SetDeviceTokenUseCase,
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val deleteDeviceTokenUseCase: DeleteDeviceTokenUseCase
) {

    @PostMapping("token/{deviceToken}")
    fun setDeviceToken(@PathVariable deviceToken: String): ResponseEntity<Void> =
        setDeviceTokenUseCase.execute(deviceToken)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @PostMapping("token/test")
    fun sendTest(): ResponseEntity<Void> =
        sendNotificationUseCase.execute(NotificationType.FIRST_NOTIFICATION)
            .run { ResponseEntity.ok().build() }

    @DeleteMapping("token")
    fun deleteDeviceToken(): ResponseEntity<Void> =
        deleteDeviceTokenUseCase.execute()
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }
}
