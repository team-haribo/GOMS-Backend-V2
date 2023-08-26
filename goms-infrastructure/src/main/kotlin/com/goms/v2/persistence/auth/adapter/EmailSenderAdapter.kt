package com.goms.v2.persistence.auth.adapter

import com.goms.v2.domain.auth.exception.EmailSendFailException
import com.goms.v2.domain.auth.spi.EmailSendPort
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import javax.mail.MessagingException

@Component
class EmailSenderAdapter(
    private val mailSender: JavaMailSender,
): EmailSendPort {

    @Async
    override fun sendEmail(email: String, authCode: String) {
        val subject = "GOMS 인증번호"
        val content = "GOMS 인증번호는 " + authCode + "입니다."
        try {
            val mimeMailMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMailMessage,true,"utf8")
            helper.setTo(email)
            helper.setSubject(subject)
            helper.setText(content)
            mailSender.send(mimeMailMessage)
        } catch (e : MessagingException) {
            throw EmailSendFailException()
        }

    }

}