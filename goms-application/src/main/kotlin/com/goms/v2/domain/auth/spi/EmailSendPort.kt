package com.goms.v2.domain.auth.spi

interface EmailSendPort {

    fun sendEmail(email: String, authCode: String)

}