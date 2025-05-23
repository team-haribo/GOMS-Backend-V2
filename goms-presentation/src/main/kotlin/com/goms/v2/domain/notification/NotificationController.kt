package com.goms.v2.domain.notification

import com.goms.v2.domain.notification.dto.request.SendCustomNotificationByDeviceTokenRequest
import com.goms.v2.domain.notification.dto.request.SendCustomNotificationRequest
import com.goms.v2.domain.notification.mapper.NotificationDataMapper
import com.goms.v2.domain.notification.usecase.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v2/notification")
class NotificationController(
    private val setDeviceTokenUseCase: SetDeviceTokenUseCase,
    private val sendCustomNotificationUseCase: SendCustomNotificationUseCase,
    private val sendNotificationByDeviceTokenUseCase: SendNotificationByDeviceTokenUseCase,
    private val deleteDeviceTokenUseCase: DeleteDeviceTokenUseCase,
    private val notificationDataMapper: NotificationDataMapper
) {
    @PostMapping("token/{deviceToken}")
    fun setDeviceToken(@PathVariable deviceToken: String): ResponseEntity<Void> =
        setDeviceTokenUseCase.execute(deviceToken)
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }

    @PostMapping("send")
    fun sendMessage(
        @RequestBody sendCustomNotificationRequest: SendCustomNotificationRequest,
        @RequestHeader("Discord-Client-Token") discordClientToken: String
    ): ResponseEntity<Void> {
        return sendCustomNotificationUseCase.execute(notificationDataMapper.toDto(
            sendCustomNotificationRequest,
            discordClientToken
        )).run {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }
    }

    @PostMapping("send/{deviceToken}")
    fun sendMessage(
        @RequestBody sendCustomNotificationByDeviceTokenRequest: SendCustomNotificationByDeviceTokenRequest,
        @RequestHeader("Discord-Client-Token") discordClientToken: String,
        @PathVariable deviceToken: String
    ): ResponseEntity<Void> {
        return sendNotificationByDeviceTokenUseCase.execute(notificationDataMapper.toDto(
            sendCustomNotificationByDeviceTokenRequest,
            discordClientToken,
            deviceToken
        )).run {
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        }
    }

    @DeleteMapping("token")
    fun deleteDeviceToken(): ResponseEntity<Void> =
        deleteDeviceTokenUseCase.execute()
            .run { ResponseEntity.status(HttpStatus.NO_CONTENT).build() }
}
