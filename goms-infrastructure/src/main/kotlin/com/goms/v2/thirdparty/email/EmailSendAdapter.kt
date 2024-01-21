package com.goms.v2.thirdparty.email

import com.goms.v2.domain.auth.exception.EmailSendFailException
import com.goms.v2.domain.auth.spi.EmailSendPort
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import javax.mail.MessagingException

@Component
class EmailSendAdapter(
    private val mailSender: JavaMailSender
): EmailSendPort {

    override fun sendEmail(email: String, authCode: String) {
        val subject = "GOMS 인증번호"
        val content = "GOMS 인증번호는 " + authCode + "입니다."
        val mimeMessage = mailSender.createMimeMessage()
        try {
            val helper = MimeMessageHelper(mimeMessage, true, "utf-8")
            helper.setTo(email)
            helper.setSubject(subject)
            helper.setText(content)
            mailSender.send(mimeMessage)
        } catch (e: MessagingException) {
            throw EmailSendFailException()
        }
    }

}