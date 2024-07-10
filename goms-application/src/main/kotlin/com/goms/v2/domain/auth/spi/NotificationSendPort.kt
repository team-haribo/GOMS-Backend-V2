package com.goms.v2.domain.auth.spi

interface NotificationSendPort {

    fun sendNotification(email: String, authCode: String)

}