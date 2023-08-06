package com.goms.v2.domain.email.spi

interface JavaMailSendPort {

    fun sendEmail(email: String, authCode: String)

}