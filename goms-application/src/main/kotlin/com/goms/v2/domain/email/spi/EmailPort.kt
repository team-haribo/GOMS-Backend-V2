package com.goms.v2.domain.email.spi

interface EmailPort {

    fun sendEmail(email: String, authKey: String)

}