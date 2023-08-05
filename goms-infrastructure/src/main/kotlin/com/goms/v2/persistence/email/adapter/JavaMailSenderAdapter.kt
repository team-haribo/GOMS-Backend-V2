package com.goms.v2.persistence.email.adapter

import com.goms.v2.domain.email.EmailAuth
import com.goms.v2.domain.email.exception.EmailSendFailException
import com.goms.v2.domain.email.exception.ManyEmailRequestException
import com.goms.v2.domain.email.spi.JavaMailSendPort
import com.goms.v2.repository.email.EmailAuthRepository
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import javax.mail.MessagingException

@Component
class JavaMailSenderAdapter(
    private val mailSender: JavaMailSender,
    private val emailAuthRepository: EmailAuthRepository
): JavaMailSendPort {

    @Async
    override fun sendEmail(email: String, authKey: String) {
        val subject = "GOMS 인증번호"
        val content = "GOMS 인증번호는 " + authKey + "입니다"
        val emailAuth = emailAuthRepository.findByIdOrNull(email)
            ?: EmailAuth(email,authKey,false,0,1800)
        if(emailAuth.attemptCount >=5) throw ManyEmailRequestException()

        emailAuth.updateAuthKey(authKey)
        emailAuth.updateAuthentication(true)
        emailAuth.increaseAttemptCount()
        emailAuthRepository.save(emailAuth)

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