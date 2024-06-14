package com.goms.v2.thirdparty.email

import com.goms.v2.domain.auth.exception.EmailSendFailException
import com.goms.v2.domain.auth.spi.EmailSendPort
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine

@Component
class EmailSendAdapter(
    private val mailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine
): EmailSendPort {

    companion object {
        val EMAIL_SUBJECT = "GOMS 이메일 인증"
    }

    @Async
    override fun sendEmail(email: String, authCode: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, "utf-8")
        helper.setTo(email)
        helper.setSubject(EMAIL_SUBJECT)
        val context = setContext(authCode)
        helper.setText(context, true)

        runCatching {
            mailSender.send(message)
        }.onFailure {
            throw EmailSendFailException()
        }
    }

    private fun setContext(authCode: String): String {
        val template = "certificationMailTemplate"
        val context = Context()

        context.setVariable("authenticationCode", authCode)

        return templateEngine.process(template, context)
    }


}