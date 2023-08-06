package com.goms.v2.domain.email.spi

interface EmailSendPort {

    fun sendEmail(email: String, authCode: String)

}